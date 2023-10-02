package com.yyy.listview.data.repository


import com.yyy.listview.data.di.IoDispatcher
import com.yyy.listview.data.local.LocalDataSource
import com.yyy.listview.data.local.entity.PostEntity
import com.yyy.listview.data.remote.RemoteDataSource
import com.yyy.listview.data.remote.model.toPostEntity
import com.yyy.listview.utils.CONNECTION_ERROR
import com.yyy.listview.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.UnknownHostException
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : PostsRepository {

    override suspend fun fetchPosts(): Resource<Unit> {
        return withContext(ioDispatcher) {
            try {
                val response = remoteDataSource.fetchPostList()
                if (response.isSuccessful) {
                    response.body()?.posts?.let { postsRemoteList ->
                        val remotePosts = postsRemoteList.map {
                            it.toPostEntity()
                        }
                        localDataSource.insertPosts(remotePosts)
                        Resource.Success(Unit)
                    } ?: Resource.Failure(Throwable("Post List Empty"))
                } else {
                    Resource.Failure(Throwable("Request is not successful"))
                }
            } catch (error: Exception) {
                if (error is UnknownHostException) {
                    Resource.Failure(Throwable(CONNECTION_ERROR))
                } else {
                    Resource.Failure(Throwable(error.message))
                }
            }
        }
    }

    override fun getPosts(): Flow<List<PostEntity>> {
        return localDataSource.getPostListFlow()
    }

}
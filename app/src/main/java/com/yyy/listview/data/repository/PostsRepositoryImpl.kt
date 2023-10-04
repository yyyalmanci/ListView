package com.yyy.listview.data.repository


import com.yyy.listview.data.di.IoDispatcher
import com.yyy.listview.data.local.LocalDataSource
import com.yyy.listview.data.local.entity.PostEntity
import com.yyy.listview.data.remote.RemoteDataSource
import com.yyy.listview.data.remote.model.toPostEntity
import com.yyy.listview.utils.CONNECTION_ERROR
import com.yyy.listview.utils.EMPTY_STRING
import com.yyy.listview.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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
                    response.body()?.let { postsRemoteList ->
                        val remotePosts = postsRemoteList.map {
                            it.toPostEntity()
                        }
                        val localPostsMap: Map<Int, PostEntity> =
                            localDataSource.getPostListFlow().first().associateBy { it.id }
                        val upToDatePosts = remotePosts.map { entity ->
                            entity.copy(
                                isDescriptionUpdated = localPostsMap[entity.id]?.isDescriptionUpdated
                                    ?: false,
                                isTitleUpdated = localPostsMap[entity.id]?.isTitleUpdated
                                    ?: false,
                                updatedDescription = localPostsMap[entity.id]?.updatedDescription
                                    ?: EMPTY_STRING,
                                updatedTitle = localPostsMap[entity.id]?.updatedTitle
                                    ?: EMPTY_STRING
                            )
                        }
                        localDataSource.insertPosts(upToDatePosts)
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

    override suspend fun updatePostTitle(title: String, id: Int) {
        withContext(ioDispatcher) {
            localDataSource.updatePostTitle(title, id)
        }
    }

    override suspend fun updateDescription(description: String, id: Int) {
        withContext(ioDispatcher) {
            localDataSource.updateDescription(description, id)
        }
    }

}
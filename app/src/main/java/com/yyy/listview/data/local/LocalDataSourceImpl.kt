package com.yyy.listview.data.local

import com.yyy.listview.data.local.entity.PostEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val dao: PostDao) : LocalDataSource {

    override fun getPostListFlow(): Flow<List<PostEntity>> = dao.getPostListFlow()


    override suspend fun insertPosts(posts: List<PostEntity>) {
        dao.insertPosts(posts)
    }

    override suspend fun updatePostTitle(title: String, id: Int) {
        dao.updateTitle(title, id)
    }

    override suspend fun updateDescription(description: String, id: Int) {
        dao.updateDescription(description, id)
    }
}
package com.yyy.listview.data.local

import com.yyy.listview.data.local.entity.PostEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getPostListFlow(): Flow<List<PostEntity>>

    suspend fun insertPosts(posts: List<PostEntity>)

    suspend fun updatePostTitle(title: String, id: Int)

    suspend fun updateDescription(description: String, id: Int)
}
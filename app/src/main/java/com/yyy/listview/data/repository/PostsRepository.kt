package com.yyy.listview.data.repository

import com.yyy.listview.data.local.entity.PostEntity
import com.yyy.listview.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    suspend fun fetchPosts(): Resource<Unit>

    fun getPosts(): Flow<List<PostEntity>>

    suspend fun updatePostTitle(title: String, id: Int)

    suspend fun updateDescription(description: String, id: Int)
}
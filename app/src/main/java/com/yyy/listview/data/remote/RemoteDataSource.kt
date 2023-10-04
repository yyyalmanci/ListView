package com.yyy.listview.data.remote

import com.yyy.listview.data.remote.model.Post
import retrofit2.Response

interface RemoteDataSource {

    suspend fun fetchPostList(): Response<List<Post>>

}
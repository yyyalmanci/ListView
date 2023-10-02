package com.yyy.listview.data.remote

import com.yyy.listview.data.remote.model.PostsResponse
import retrofit2.Response

interface RemoteDataSource {

    suspend fun fetchPostList(): Response<PostsResponse>

}
package com.yyy.listview.data.remote

import com.yyy.listview.data.remote.model.PostsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsService {

    @GET("posts")
    suspend fun fetchPosts(): Response<PostsResponse>
}
package com.yyy.listview.data.remote

import com.yyy.listview.data.remote.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface PostsService {

    @GET("posts")
    suspend fun fetchPosts(): Response<List<Post>>

}
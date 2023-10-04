package com.yyy.listview.data.local

import com.yyy.listview.data.remote.RemoteDataSource
import com.yyy.listview.data.remote.model.Post
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

class FakeRemoteDataStore : RemoteDataSource {

    private val posts = mutableListOf<Post>()

    override suspend fun fetchPostList(): Response<List<Post>> {
           return try {
                posts.addAll(
                    listOf(
                        Post(
                            1,
                            1,
                            "title1",
                            "body1",),
                        Post(
                            2,
                            2,
                            "title2",
                            "body2",)
                    )
                )
                Response.success(posts)
            } catch (error: Exception) {
                Response.error(1,null)
            }
    }
}
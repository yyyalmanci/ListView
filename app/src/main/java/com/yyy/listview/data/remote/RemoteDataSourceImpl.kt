package com.yyy.listview.data.remote


import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val postsService: PostsService,
) : RemoteDataSource {

    override suspend fun fetchPostList() = postsService.fetchPosts()

}
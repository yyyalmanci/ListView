package com.yyy.listview.domain

import com.yyy.listview.data.repository.PostsRepository
import com.yyy.listview.utils.Resource
import javax.inject.Inject

class FetchPostsUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {

   suspend operator fun invoke(): Resource<Unit> =
        postsRepository.fetchPosts()
}
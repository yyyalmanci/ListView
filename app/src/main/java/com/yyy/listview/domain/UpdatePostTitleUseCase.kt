package com.yyy.listview.domain

import com.yyy.listview.data.repository.PostsRepository
import javax.inject.Inject

class UpdatePostTitleUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    suspend operator fun invoke(title: String, id: Int) {
        postsRepository.updatePostTitle(title, id)
    }
}
package com.yyy.listview.domain

import com.yyy.listview.data.repository.PostsRepository
import javax.inject.Inject

class UpdateDescriptionUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    suspend operator fun invoke(description: String, id: Int) {
        postsRepository.updateDescription(description, id)
    }
}
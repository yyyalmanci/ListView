package com.yyy.listview.domain


import com.yyy.listview.data.local.entity.toPostDomainModel
import com.yyy.listview.data.repository.PostsRepository
import com.yyy.listview.domain.model.PostDomainModel
import com.yyy.listview.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    operator fun invoke(): Flow<Resource<List<PostDomainModel>>> =
        try {
            postsRepository.getPosts().map { posts ->
                Resource.Success(posts.map { it.toPostDomainModel() })
            }
        } catch (t: Throwable) {
            flowOf(Resource.Failure(t))
        }
}
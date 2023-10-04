package com.yyy.listview.data.local

import com.yyy.listview.data.local.entity.PostEntity
import com.yyy.listview.utils.EMPTY_STRING
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeLocalDataSource : LocalDataSource {

    private val postList = mutableListOf(
        PostEntity(
            1,
            "title1",
            "body1",
            updatedTitle = "updatedtitle",
            isTitleUpdated = true,
            updatedDescription = "updateddesc",
            isDescriptionUpdated = true
        ),
        PostEntity(
            2,
            "title2",
            "body2",
            updatedTitle = EMPTY_STRING,
            isTitleUpdated = false,
            updatedDescription = EMPTY_STRING,
            isDescriptionUpdated = false
        )
    )

    override fun getPostListFlow(): Flow<List<PostEntity>> {
        return flowOf(postList)
    }

    override suspend fun insertPosts(posts: List<PostEntity>) {
        postList.clear()
        postList.addAll(posts)
    }

    override suspend fun updatePostTitle(title: String, id: Int) {
        val updatedList = postList.map {
            if (it.id == id) {
                it.copy(
                    title = title,
                    isTitleUpdated = true
                )
            } else {
                it.copy()
            }
        }
        postList.clear()
        postList.addAll(
            updatedList
        )
    }

    override suspend fun updateDescription(description: String, id: Int) {
        val updatedList = postList.map {
            if (it.id == id) {
                it.copy(
                    description = description,
                    isDescriptionUpdated = true
                )
            } else {
                it.copy()
            }
        }
        postList.clear()
        postList.addAll(
            updatedList
        )
    }
}
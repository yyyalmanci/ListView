package com.yyy.listview.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yyy.listview.domain.model.PostDomainModel

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val updatedTitle: String,
    val isTitleUpdated: Boolean = false,
    val updatedDescription: String,
    val isDescriptionUpdated: Boolean = false,
)

fun PostEntity.toPostDomainModel() = PostDomainModel(
    id,
    title,
    description,
    updatedTitle,
    isTitleUpdated,
    updatedDescription,
    isDescriptionUpdated
)
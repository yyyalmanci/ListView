package com.yyy.listview.data.remote.model

import com.google.gson.annotations.SerializedName
import com.yyy.listview.data.local.entity.PostEntity

data class Post(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
)

fun Post.toPostEntity() = PostEntity(
    id, title, body
)
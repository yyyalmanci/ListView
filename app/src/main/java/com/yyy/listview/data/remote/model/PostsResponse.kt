package com.yyy.listview.data.remote.model

import com.google.gson.annotations.SerializedName

data class PostsResponse(
    @SerializedName("")
    val posts: List<Post>,
)

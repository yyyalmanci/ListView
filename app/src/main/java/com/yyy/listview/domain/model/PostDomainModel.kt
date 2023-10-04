package com.yyy.listview.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostDomainModel(
    val id: Int,
    val title: String,
    val description: String,
    val updatedTitle: String,
    val isTitleUpdated: Boolean = false,
    val updatedDescription: String,
    val isDescriptionUpdated: Boolean = false,
) : Parcelable
package com.yyy.listview.presentation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.yyy.listview.domain.model.PostDomainModel

class PostDiffItemCallback : DiffUtil.ItemCallback<PostDomainModel>() {
    override fun areItemsTheSame(oldItem: PostDomainModel, newItem: PostDomainModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PostDomainModel, newItem: PostDomainModel): Boolean =
        oldItem == newItem
}
package com.yyy.listview.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.yyy.listview.R
import com.yyy.listview.databinding.ItemPostBinding
import com.yyy.listview.domain.model.PostDomainModel
import com.yyy.listview.presentation.adapter.diffutil.PostDiffItemCallback


class PostsAdapter constructor(
    private val onItemClickListener: (
        id: Int,
        title: String,
        desc: String
    ) -> Unit,
) : ListAdapter<PostDomainModel, PostsAdapter.PostViewHolder>(PostDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostDomainModel) {
            binding.apply {
                title.text = item.title
                desc.text = item.description
                val imageUrl = "https://picsum.photos/300/300?random=$${adapterPosition}&grayscale"
                image.load(imageUrl){
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_background)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }
}
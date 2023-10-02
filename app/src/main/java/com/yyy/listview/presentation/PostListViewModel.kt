package com.yyy.listview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yyy.listview.domain.FetchPostsUseCase
import com.yyy.listview.domain.GetPostsUseCase
import com.yyy.listview.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val fetchPostsUseCase: FetchPostsUseCase,
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    init {
        fetchPosts()
    }
    private fun fetchPosts() {
        viewModelScope.launch {
            delay(10000)
            when (val responsePosts = fetchPostsUseCase()) {
                is Resource.Failure -> {
                    Timber.d("error while fetching post list ${responsePosts.throwable.message}")
                }
                is Resource.Success -> {
                    Timber.d("fetching post list success")
                }
            }
        }
    }
}
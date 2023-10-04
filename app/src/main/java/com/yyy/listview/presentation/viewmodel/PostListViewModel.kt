package com.yyy.listview.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yyy.listview.domain.FetchPostsUseCase
import com.yyy.listview.domain.GetPostsUseCase
import com.yyy.listview.presentation.model.PostListUiState
import com.yyy.listview.utils.CONNECTION_ERROR
import com.yyy.listview.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val fetchPostsUseCase: FetchPostsUseCase,
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    private var _postListFlow = MutableStateFlow<PostListUiState>(PostListUiState.Idle)
    val postListFlow = _postListFlow.asStateFlow()

    private var _connectionError = MutableSharedFlow<Boolean>()
    val connectionError = _connectionError.asSharedFlow()

    fun fetchPosts() {
        viewModelScope.launch {
            when (val responsePosts = fetchPostsUseCase()) {
                is Resource.Failure -> {
                    Timber.d("error while fetching post list ${responsePosts.throwable.message}")
                    if (responsePosts.throwable.message == CONNECTION_ERROR){
                        _connectionError.emit(true)
                    }
                }
                is Resource.Success -> {
                    Timber.d("fetching post list success")
                }
            }
        }
    }

    fun getPosts() {
        viewModelScope.launch {
            _postListFlow.value = PostListUiState.Loading
            getPostsUseCase().collect { resourcePostList ->
                when (resourcePostList) {
                    is Resource.Failure -> {
                        Timber.d("error while getting data from db ${resourcePostList.throwable.message}")
                    }
                    is Resource.Success -> {
                        _postListFlow.value = PostListUiState.Success(resourcePostList.data)
                    }
                }
            }
        }
    }
}
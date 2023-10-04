package com.yyy.listview.presentation.model

import com.yyy.listview.domain.model.PostDomainModel

sealed class PostListUiState {
    data object Loading : PostListUiState()
    class Success(val postList: List<PostDomainModel>) : PostListUiState()
    data object Idle : PostListUiState()
}
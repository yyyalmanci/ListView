package com.yyy.listview.data.repositoy

import com.google.common.truth.Truth.assertThat
import com.yyy.listview.data.local.FakeLocalDataSource
import com.yyy.listview.data.local.FakeRemoteDataStore
import com.yyy.listview.data.local.LocalDataSource
import com.yyy.listview.data.remote.RemoteDataSource
import com.yyy.listview.data.repository.PostsRepository
import com.yyy.listview.data.repository.PostsRepositoryImpl
import com.yyy.listview.util.MainDispatcherRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostsRepositoryTest() {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var remoteDataStore: RemoteDataSource
    private lateinit var localDataSource: LocalDataSource
    private lateinit var postsRepository: PostsRepository

    @Before
    fun setUp() {
        remoteDataStore = FakeRemoteDataStore()
        localDataSource = FakeLocalDataSource()
        postsRepository = PostsRepositoryImpl(remoteDataStore, localDataSource, Dispatchers.IO)
    }

    @Test
    fun whenFetchPosts_WeShouldSeeUpdatedLocalData_asUpdated() = runTest {
        postsRepository.fetchPosts()
        val list = postsRepository.getPosts().first()
        assertThat(true).isEqualTo(list.find { it.id == 1 }?.isTitleUpdated)
    }

    @Test
    fun whenFetchPosts_WeShouldSeeNotUpdatedLocalData_asNotUpdated() = runTest {
        postsRepository.fetchPosts()
        val list = postsRepository.getPosts().first()
        assertThat(false).isEqualTo(list.find { it.id == 2 }?.isTitleUpdated)
    }

    @Test
    fun whenUpdateData_WeShouldSeeUpdatedData() = runTest {
        postsRepository.fetchPosts()
        val list = postsRepository.getPosts().first()
        assertThat(false).isEqualTo(list.find { it.id == 2 }?.isTitleUpdated)
        postsRepository.updatePostTitle("newtitle",2)
        val updatedList = postsRepository.getPosts().first()
        assertThat(true).isEqualTo(updatedList.find { it.id == 2 }?.isTitleUpdated)
        assertThat("newtitle").isEqualTo(updatedList.find { it.id == 2 }?.title)
    }
}
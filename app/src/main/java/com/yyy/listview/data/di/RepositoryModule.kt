package com.yyy.listview.data.di

import com.yyy.listview.data.repository.PostsRepository
import com.yyy.listview.data.repository.PostsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPostsRepository(postsRepositoryImpl: PostsRepositoryImpl): PostsRepository

}
package com.yyy.listview.data.di

import android.content.Context
import androidx.room.Room
import com.yyy.listview.data.local.PostDao
import com.yyy.listview.data.local.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): PostDatabase =
        Room.databaseBuilder(
            context, PostDatabase::class.java, PostDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun providePostDao(database: PostDatabase): PostDao = database.postDao()
}
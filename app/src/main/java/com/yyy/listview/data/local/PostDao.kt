package com.yyy.listview.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yyy.listview.data.local.entity.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM posts ")
    fun getPostListFlow(): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Query("UPDATE posts SET description = :description WHERE id = :id")
    suspend fun updateDescription(description: String, id: Int)

    @Query("UPDATE posts SET title = :title WHERE id = :id")
    suspend fun updateTitle(title: String, id: Int)

}
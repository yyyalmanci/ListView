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

    @Query("UPDATE posts SET updatedDescription = :description, isDescriptionUpdated = :isUpdated  WHERE id = :id")
    suspend fun updateDescription(description: String, id: Int, isUpdated: Boolean = true)

    @Query("UPDATE posts SET updatedTitle = :title, isTitleUpdated = :isUpdated WHERE id = :id")
    suspend fun updateTitle(title: String, id: Int, isUpdated: Boolean = true)

}
package com.yyy.listview.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yyy.listview.data.local.entity.PostEntity

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        const val DATABASE_NAME = "postsDb"
    }
}
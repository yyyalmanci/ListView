package com.yyy.listview.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.yyy.listview.data.local.entity.PostEntity
import com.yyy.listview.utils.EMPTY_STRING
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class PostDaoTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var postDao: PostDao
    private lateinit var database: PostDatabase
    private lateinit var postList: List<PostEntity>

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), PostDatabase::class.java
        ).allowMainThreadQueries().build()
        postDao = database.postDao()
        postList = listOf(
            PostEntity(
                1,
                "title1",
                "body1",
                updatedTitle = "updatedtitle",
                isTitleUpdated = true,
                updatedDescription = "updateddesc",
                isDescriptionUpdated = true
            ),
            PostEntity(
                2,
                "title2",
                "body2",
                updatedTitle = EMPTY_STRING,
                isTitleUpdated = false,
                updatedDescription = EMPTY_STRING,
                isDescriptionUpdated = false
            )
        )
    }

    @After
    fun tearDown() = database.close()

    @Test
    fun insertPostListShouldReturnListFlow() = runBlocking {
        postDao.insertPosts(postList)

        postDao.getPostListFlow().test {
            val postList = awaitItem()
            assertThat(true).isEqualTo(postList.contains(postList[0]))
            assertThat(true).isEqualTo(postList.contains(postList[1]))
            cancel()
        }
    }


    @Test
    fun updatedItemShouldBeExist() = runBlocking {
        postDao.insertPosts(postList)

        postDao.getPostListFlow().test {
            val postList = awaitItem()
            assertThat("title1").isEqualTo(postList.find { it.id == 1 }?.title)
            assertThat("body2").isEqualTo(postList.find { it.id == 2 }?.description)
            cancel()
        }

        postDao.updateTitle("1title",1)
        postDao.updateDescription("2post",2)

        postDao.getPostListFlow().test {
            val postList = awaitItem()
            assertThat("1title").isEqualTo(postList.find { it.id == 1}?.updatedTitle)
            assertThat(true).isEqualTo(postList.find { it.id == 1}?.isTitleUpdated)
            assertThat("2post").isEqualTo(postList.find { it.id == 2 }?.updatedDescription)
            assertThat(true).isEqualTo(postList.find { it.id == 2}?.isDescriptionUpdated)
            cancel()
        }
    }

}
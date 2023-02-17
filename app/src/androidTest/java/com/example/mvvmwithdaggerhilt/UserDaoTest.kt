package com.example.mvvmwithdaggerhilt

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Database
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.mvvmwithdaggerhilt.api.UserDao
import com.example.mvvmwithdaggerhilt.data.database.AppDatabase
import com.example.mvvmwithdaggerhilt.ui.model.UserRequest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.*
import javax.inject.Inject

@HiltAndroidTest
class UserDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var database: AppDatabase
    lateinit var userDao: UserDao

    @Before
    fun setUp(){
        /*database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()*/
        hiltAndroidRule.inject()

        userDao = database.userDao()
    }

    @Test
    fun insertUser_returnSingleUser() = runTest{
        val user = UserRequest(id = 1, email = "pro@gmail.com", username = "Pro", password = "Pro@123")
        userDao.registerUser(user)
        val user2 = UserRequest(id = 2, email = "pro@gmail.com", username = "Pro", password = "Pro@123")
        userDao.registerUser(user2)
        val result = userDao.getUsers()
        Assert.assertEquals(2, result.size)
    }

    @After
    fun closeDatabase(){
        database.close()
    }
}
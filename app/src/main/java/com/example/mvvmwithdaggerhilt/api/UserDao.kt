package com.example.mvvmwithdaggerhilt.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmwithdaggerhilt.ui.model.UserRequest
import com.example.mvvmwithdaggerhilt.ui.model.UserResponseModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUser(userRequest: UserRequest):Long

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun loginUser(email:String, password:String) : UserRequest?

    @Query("SELECT * FROM user")
    suspend fun getUsers():List<UserRequest>
}
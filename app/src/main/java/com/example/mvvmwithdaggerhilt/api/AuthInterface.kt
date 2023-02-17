package com.example.mvvmwithdaggerhilt.api

import com.example.mvvmwithdaggerhilt.ui.model.UserRequest
import com.example.mvvmwithdaggerhilt.ui.model.UserResponseModel
import retrofit2.Response

interface AuthInterface {
    suspend fun registerUser(userRequest: UserRequest)
    suspend fun loginUser(userRequest: UserRequest)
}
package com.example.mvvmwithdaggerhilt.api

import com.example.mvvmwithdaggerhilt.ui.model.UserRequest
import com.example.mvvmwithdaggerhilt.ui.model.UserResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/api/register")
    suspend fun registerUser(@Body userRequest: UserRequest) : Response<UserResponseModel>

    @POST("/api/login")
    suspend fun loginUser(@Body userRequest: UserRequest) : Response<UserResponseModel>
}
package com.example.mvvmwithdaggerhilt.api


import com.example.mvvmwithdaggerhilt.ui.model.UserListResponseModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ListOfDataApi {
    @GET("/v1/passenger")
    suspend fun getUserList(@Query("page") pageNo:Int, @Query("size") pageSize:Int) : Response<UserListResponseModel>
}
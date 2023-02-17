package com.example.mvvmwithdaggerhilt.data.repositories

import android.nfc.Tag
import com.example.mvvmwithdaggerhilt.api.AuthInterface
import com.example.mvvmwithdaggerhilt.api.UserApi
import com.example.mvvmwithdaggerhilt.ui.model.UserRequest
import com.example.mvvmwithdaggerhilt.ui.model.UserResponseModel
import com.example.mvvmwithdaggerhilt.utils.NetworkResult
import com.example.mvvmwithdaggerhilt.utils.Utils.setLog
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Response
import javax.inject.Inject

class AuthServerRepository @Inject constructor(private val userApi: UserApi):AuthInterface {
    private val TAG = "AuthServerRepository"
    private val _registerUser = MutableSharedFlow<NetworkResult<UserResponseModel>>()
    val registerUser = _registerUser.asSharedFlow()

    private val _loginUser = MutableSharedFlow<NetworkResult<UserResponseModel>>()
    val loginUser = _loginUser.asSharedFlow()

    override suspend fun registerUser(userRequest: UserRequest) {
        _registerUser.emit(NetworkResult.Loading())
        setLog(TAG, "registerUser-${Thread.currentThread().name}")
        val result = userApi.registerUser(userRequest)
        _registerUser.emit(handleResponse(result))
    }

    override suspend fun loginUser(userRequest: UserRequest) {
        _loginUser.emit(NetworkResult.Loading())
        setLog(TAG, "loginUser-${Thread.currentThread().name}")
        val result = userApi.loginUser(userRequest)
        _loginUser.emit(handleResponse(result))
    }

    private fun handleResponse(result: Response<UserResponseModel>): NetworkResult<UserResponseModel> {
        try {
            if (result.isSuccessful && result.body() != null){
                return NetworkResult.Success(result.body())
            }else if (result.errorBody() != null){
                val errorData = Gson().fromJson(result.errorBody()?.charStream()!!.readText(), UserResponseModel::class.java)
                return NetworkResult.Error(errorData, "Something went wrong")
            }
        }catch (e:Exception){

        }
        return NetworkResult.Error(message = "Something went wrong")
    }

}
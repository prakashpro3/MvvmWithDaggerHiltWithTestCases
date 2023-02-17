package com.example.mvvmwithdaggerhilt.data.repositories

import com.example.mvvmwithdaggerhilt.api.AuthInterface
import com.example.mvvmwithdaggerhilt.api.UserDao
import com.example.mvvmwithdaggerhilt.ui.model.UserRequest
import com.example.mvvmwithdaggerhilt.ui.model.UserResponseModel
import com.example.mvvmwithdaggerhilt.utils.NetworkResult
import com.example.mvvmwithdaggerhilt.utils.Utils.setLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AuthDatabaseRepository @Inject constructor(private val userDao: UserDao):AuthInterface {
    private val TAG = "AuthDatabaseRepository"
    private val _registerUser = MutableSharedFlow<NetworkResult<UserResponseModel>>()
    val registerUser = _registerUser.asSharedFlow()

    private val _loginUser = MutableSharedFlow<NetworkResult<UserResponseModel>>()
    val loginUser = _loginUser.asSharedFlow()

    override suspend fun registerUser(userRequest: UserRequest) {
        _registerUser.emit(NetworkResult.Loading())
        delay(3000)
        setLog(TAG, "registerUser-${Thread.currentThread().name}")
        setLog(TAG, "registerUser-A")
        val result = userDao.registerUser(userRequest)
        setLog(TAG, "registerUser-B-$result")
        val id = result.toInt()
        setLog(TAG, "registerUser-C-$id")
        if (result > 0) {
            setLog(TAG, "registerUser-D-$id")
            //_registerUser.value = NetworkResult.Success(UserResponseModel("", id, "database token"))
            _registerUser.emit(NetworkResult.Success(UserResponseModel("", id, "database token")))
            setLog(TAG, "registerUser-E-$id")
        }else{
            setLog(TAG, "registerUser-F-$id")
            //_registerUser.value = NetworkResult.Error(UserResponseModel("No user found in db", 0, ""))
            _registerUser.emit(NetworkResult.Error(UserResponseModel("No user found in db", 0, "")))
        }
        setLog(TAG, "registerUser-G-$id")
    }

    override suspend fun loginUser(userRequest: UserRequest) {
        _loginUser.emit(NetworkResult.Loading())
        delay(3000)
        setLog(TAG, "loginUser-${Thread.currentThread().name}")
        val result = userDao.loginUser(userRequest.email, userRequest.password)
        setLog(TAG, "loginUser-$result")
        result?.let {
            _loginUser.emit(NetworkResult.Success(UserResponseModel("", it.id, "database token")))
        } ?: run {
            _loginUser.emit(NetworkResult.Error(UserResponseModel("No user found in db", 0, "")))

        }

    }
}
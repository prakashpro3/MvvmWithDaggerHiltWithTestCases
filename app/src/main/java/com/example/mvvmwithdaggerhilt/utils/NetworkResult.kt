package com.example.mvvmwithdaggerhilt.utils

sealed class NetworkResult<T>(val data:T? = null, val message:String? = null) {
    class Success<T>(data: T?) : NetworkResult<T>(data)
    class Error<T>(data: T? = null, message: String?=null) : NetworkResult<T>(data, message)
    class Loading<T>:NetworkResult<T>()
}

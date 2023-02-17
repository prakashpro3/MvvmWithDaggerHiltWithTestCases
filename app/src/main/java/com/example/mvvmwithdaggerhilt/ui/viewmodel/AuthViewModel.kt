package com.example.mvvmwithdaggerhilt.ui.viewmodel

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.example.mvvmwithdaggerhilt.data.repositories.AuthDatabaseRepository
import com.example.mvvmwithdaggerhilt.data.repositories.AuthServerRepository
import com.example.mvvmwithdaggerhilt.ui.model.UserRequest
import com.example.mvvmwithdaggerhilt.ui.model.UserResponseModel
import com.example.mvvmwithdaggerhilt.utils.NetworkResult
import com.example.mvvmwithdaggerhilt.utils.Utils
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthServerRepository):ViewModel() {
    private val TAG = "AuthViewModel"
   // private val _createUser = MutableStateFlow<NetworkResult<UserResponseModel>>(NetworkResult.Loading())
    val registerUser = authRepository.registerUser

    //private val _loginUser = MutableStateFlow<NetworkResult<UserResponseModel>>(NetworkResult.Loading())
    val loginUser = authRepository.loginUser

    suspend fun registerUser(userRequest: UserRequest){
        Utils.setLog(TAG, "registerUser-${Thread.currentThread().name}")
        authRepository.registerUser(userRequest)
        /*if (result.isSuccessful && result.body() != null){
            _createUser.value = NetworkResult.Success(result.body())
        }else if (result.errorBody() != null){
            val errorData = Gson().fromJson(result.errorBody()?.charStream()!!.readText(), UserResponseModel::class.java)
            _createUser.value = NetworkResult.Error(errorData, "Something went wrong")
        }else{
            _createUser.value = NetworkResult.Error(message = "Something went wrong")
        }*/
    }

    suspend fun loginUser(userRequest: UserRequest){
        Utils.setLog(TAG, "registerUser-${Thread.currentThread().name}")
        authRepository.loginUser(userRequest)
    }

    fun validateCredentials(emailAddress: String, userName: String, password: String,
                            isLogin: Boolean) : Pair<Boolean, String> {

        var result = Pair(true, "")
        if(TextUtils.isEmpty(userName)){
            result = Pair(false, "Please provide username")
        }else if(!isLogin && TextUtils.isEmpty(emailAddress)){
            result = Pair(false, "Please provide email")
        }else if(TextUtils.isEmpty(password)){
            result = Pair(false, "Please provide password")
        }
        /*else if(!Helper.isValidEmail(emailAddress)){
            result = Pair(false, "Email is invalid")
        }
        else if(!TextUtils.isEmpty(password) && password.length <= 5){
            result = Pair(false, "Password length should be greater than 5")
        }*/
        return result
    }
}
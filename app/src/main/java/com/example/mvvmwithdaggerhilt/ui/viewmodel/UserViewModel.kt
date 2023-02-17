package com.example.mvvmwithdaggerhilt.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.mvvmwithdaggerhilt.data.repositories.UserRepository
import com.example.mvvmwithdaggerhilt.ui.model.Data
import com.example.mvvmwithdaggerhilt.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel() {
    private val TAG = "UserViewModel"
    val getUserList get() = userRepository.getUserList
    suspend fun getUserList(pageNo:Int, pageSize:Int){
        //Utils.setLog(TAG, "Body-getUserList()")
        userRepository.getUserList(pageNo, pageSize)
    }

    suspend fun getUserListWithPaging() : Flow<PagingData<Data>> {
        //return userRepository.getUserListWithPaging(viewModelScope)
        return userRepository.getUserListWithPagingAndRoom(viewModelScope)
    }

}
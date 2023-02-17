package com.example.mvvmwithdaggerhilt.data.repositories

import androidx.paging.*
import com.example.mvvmwithdaggerhilt.api.ListOfDataApi
import com.example.mvvmwithdaggerhilt.data.database.AppDatabase
import com.example.mvvmwithdaggerhilt.ui.model.Data
import com.example.mvvmwithdaggerhilt.ui.model.UserListResponseModel
import com.example.mvvmwithdaggerhilt.utils.NetworkResult
import com.example.mvvmwithdaggerhilt.utils.PagingDataListRepository
import com.example.mvvmwithdaggerhilt.utils.Utils.setLog
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class UserRepository @Inject constructor(private val listOfDataApi: ListOfDataApi, private val appDatabase: AppDatabase?) {
    private val TAG = "UserRepository"
    private var _getUserList = MutableSharedFlow<NetworkResult<UserListResponseModel>>()
    val getUserList get() = _getUserList.asSharedFlow()
    suspend fun getUserList(pageNo:Int, pageSize:Int){
        //setLog(TAG, "Body-getUserList()")
        val result = listOfDataApi.getUserList(pageNo, pageSize)
        //setLog(TAG, "raw-${result.raw()}")
        _getUserList.emit(handleResponse(result))
    }

    private fun handleResponse(result: Response<UserListResponseModel>): NetworkResult<UserListResponseModel> {
        try {
            if (result.isSuccessful && result.body() != null){
                //setLog(TAG, "Body-${result.body()}")
                return NetworkResult.Success(result.body())
            }else if (result.errorBody() != null){
                //setLog(TAG, "ErrorBody-${result.errorBody()}")
                val errorData = Gson().fromJson(result.errorBody()?.charStream()!!.readText(), UserListResponseModel::class.java)
                return NetworkResult.Error(message = "Some Error")
            }
        }catch (e:Exception){

        }
        return NetworkResult.Error(message = "Something went wrong")
    }

    suspend fun getUserListWithPaging(viewModelScope: CoroutineScope): Flow<PagingData<Data>> {
        return Pager(config = PagingConfig(10, enablePlaceholders = false)){
            PagingDataListRepository(listOfDataApi)
        }.flow.cachedIn(viewModelScope)
    }

    @OptIn(ExperimentalPagingApi::class)
    suspend fun getUserListWithPagingAndRoom(viewModelScope: CoroutineScope): Flow<PagingData<Data>> {
        return Pager(config = PagingConfig(10, enablePlaceholders = false),
        remoteMediator = UserDataListMediator(db = appDatabase!!,listOfDataApi = listOfDataApi)){
            appDatabase.userDataListDao().getAllUserData()
        }.flow.cachedIn(viewModelScope)
    }
}
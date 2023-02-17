package com.example.mvvmwithdaggerhilt.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mvvmwithdaggerhilt.api.ListOfDataApi
import com.example.mvvmwithdaggerhilt.api.RemoteKeyDao
import com.example.mvvmwithdaggerhilt.data.RemoteKeys
import com.example.mvvmwithdaggerhilt.data.database.AppDatabase
import com.example.mvvmwithdaggerhilt.ui.model.Data
import com.example.mvvmwithdaggerhilt.utils.Utils.setLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserDataListMediator constructor(
    private val db: AppDatabase,
    private val listOfDataApi: ListOfDataApi
    ) : RemoteMediator<Int, Data>() {
    val TAG = "UserDataListMediator"
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Data>): MediatorResult {
       val page = when(val pageData = getKeyPageData(loadType, state)){
           is MediatorResult.Success->{
               return pageData
           }
           else -> {
               pageData as Int
           }
       }
        setLog(TAG, "load-page-$page")
        try {
            setLog(TAG, "load-page-$page and limit-${state.config.pageSize}")
            val response = listOfDataApi.getUserList(page, state.config.pageSize)
            var isEndOfList = false
            var data:List<Data>? = null
            if (response.isSuccessful && response.body() != null){
                data = response.body()?.data
                isEndOfList = data?.isEmpty()!!
            }else{
                isEndOfList = true
            }
            setLog(TAG, "load-data-$data")
            setLog(TAG, "load-isEndOfList-$isEndOfList")
            db.withTransaction {
                setLog(TAG, "load-loadType-${loadType.name}")
                if (loadType == LoadType.REFRESH){
                    db.userDataListDao().deleteAll()
                    db.remoteKeyDao().clearAll()
                }

                val prevKey = if (page == 1) null else page-1
                val nextKey = if (isEndOfList) null else page+1
                setLog(TAG, "load-prevKey-$prevKey and nextkey-$nextKey")
                val keys = data?.map {
                    RemoteKeys(it._id, prevKey, nextKey)
                }
                setLog(TAG, "load-keys-$keys")
                if (keys != null) {
                    db.remoteKeyDao().insertAll(keys)
                }
                if (data != null) {
                    db.userDataListDao().addAllUserData(data)
                }
            }


            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        }catch (e:Exception){
            setLog(TAG, "load-Error-${e.message}")
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Data>): Any {
        return when(loadType){
            LoadType.REFRESH -> {
                val remoteKey = getRefreshRemoteKey(state)
                remoteKey?.nextKey?.minus(1)?:1
            }
            LoadType.PREPEND -> {
                val remoteKey = getFirstRemoteKey(state)
                val prevKey = remoteKey?.prevKey ?:MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKey = getLastRemoteKey(state)
                val nextKey = remoteKey?.nextKey ?:MediatorResult.Success(
                    endOfPaginationReached = true
                )
                nextKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, Data>) : RemoteKeys? {
        return withContext(Dispatchers.IO){
            state.pages
                .firstOrNull(){
                    it.data.isNotEmpty()
                }?.data?.firstOrNull()
                ?.let {
                    db.remoteKeyDao().getAllRemoteKey(it._id)
                }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Data>) : RemoteKeys? {
        return withContext(Dispatchers.IO){
            state.pages
                .lastOrNull(){
                    it.data.isNotEmpty()
                }?.data?.lastOrNull()
                ?.let {
                    db.remoteKeyDao().getAllRemoteKey(it._id)
                }
        }
    }

    private suspend fun getRefreshRemoteKey(state: PagingState<Int, Data>) : RemoteKeys? {
        return withContext(Dispatchers.IO){
            state.anchorPosition?.let {
                state.closestItemToPosition(it)?._id?.let {
                    db.remoteKeyDao().getAllRemoteKey(it)
                }
            }
        }
    }
}
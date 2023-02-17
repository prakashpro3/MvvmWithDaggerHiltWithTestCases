package com.example.mvvmwithdaggerhilt.utils

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.mvvmwithdaggerhilt.api.ListOfDataApi
import com.example.mvvmwithdaggerhilt.ui.model.Data
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagingDataListRepository @Inject constructor(private val listOfDataApi: ListOfDataApi):
    PagingSource<Int, Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val position = params.key ?:1
            val  response = listOfDataApi.getUserList(position, params.loadSize)
            LoadResult.Page(data = response.body()?.data!!,
                prevKey = if (position == 1 ) null else position-1,
                nextKey = if (!response.isSuccessful) null else position+1)
        }catch (e:Exception){
            LoadResult.Error(e)
        }

    }

}
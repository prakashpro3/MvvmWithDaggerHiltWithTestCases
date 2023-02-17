package com.example.mvvmwithdaggerhilt.api

import androidx.paging.PagingSource
import androidx.room.*
import com.example.mvvmwithdaggerhilt.ui.model.Data

@Dao
interface UserDataListDao {

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun addAllUserData(data:List<Data>)

    @Query("SELECT * FROM userdatalist")
    fun getAllUserData():PagingSource<Int, Data>

    @Query("DELETE FROM userdatalist")
    fun deleteAll()
}
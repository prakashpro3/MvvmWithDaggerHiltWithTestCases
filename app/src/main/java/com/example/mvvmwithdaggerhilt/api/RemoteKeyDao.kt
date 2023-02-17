package com.example.mvvmwithdaggerhilt.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmwithdaggerhilt.data.RemoteKeys
import com.example.mvvmwithdaggerhilt.ui.model.Data

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userDataList:List<RemoteKeys>)

    @Query("SELECT * FROM remotekey WHERE id =:id")
    suspend fun getAllRemoteKey(id:String):RemoteKeys

    @Query("DELETE FROM remotekey")
    suspend fun clearAll()
}
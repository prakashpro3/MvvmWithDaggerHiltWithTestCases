package com.example.mvvmwithdaggerhilt.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remotekey")
data class RemoteKeys(
    @PrimaryKey
    val id:String,
    val prevKey:Int?,
    val nextKey:Int?,

)

package com.example.mvvmwithdaggerhilt.ui.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userdatalist")
data class Data(
    val __v: Int,
    @PrimaryKey
    val _id: String,
    //val airline: List<Airline>,
    val name: String,
    val trips: Long
)
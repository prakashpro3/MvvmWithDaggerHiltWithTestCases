package com.example.mvvmwithdaggerhilt.demo

import androidx.room.*

@Entity(tableName = "person")
data class PersonModel(
    @PrimaryKey(autoGenerate = true)
    var userId:Long = 0,
    var username:String,
    @ColumnInfo(name = "password")
    var password:String,
    @Embedded
    var address:Address,
    var sector:ArrayList<Sectors>
){
    @Ignore
    var confirmPassword:String = ""
}

data class Address(var city:String, var state:String)

data class Sectors(var name:String)
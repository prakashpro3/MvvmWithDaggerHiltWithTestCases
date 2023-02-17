package com.example.mvvmwithdaggerhilt.demo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonDao {

    @Insert
    fun insertUser(personModel: PersonModel)

    @Query("SELECT * FROM person")
    fun getUser() : List<PersonModel>
}
package com.example.mvvmwithdaggerhilt.demo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {
    @androidx.room.TypeConverter
    fun listToJson(list:ArrayList<Sectors>): String? {
        return Gson().toJson(list)
    }

    @androidx.room.TypeConverter
    fun jsonToList(json:String): ArrayList<Sectors> {
        val baseType = object : TypeToken<ArrayList<Sectors>>() {}.type
        return Gson().fromJson(json, baseType)
    }
}
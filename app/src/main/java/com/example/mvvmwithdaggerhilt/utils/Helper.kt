package com.example.mvvmwithdaggerhilt.utils

import android.content.Context
import com.example.mvvmwithdaggerhilt.R

class Helper {

    fun isPalindrome(value:String): Boolean {
        var i = 0
        var j = value.length - 1
        var result = true

        while (i < j){
            if (value[i] != value[j]){
                result = false
                break
            }
            i++
            j--
        }
        return result
    }

    fun getStrings(context: Context):String{
        return context.getString(R.string.app_name)
    }
}
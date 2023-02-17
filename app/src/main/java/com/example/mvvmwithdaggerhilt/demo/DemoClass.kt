package com.example.mvvmwithdaggerhilt.demo

import javax.inject.Inject

class DemoClass @Inject constructor() {
    fun getData():String{
        return "Welcome to"
    }
}
package com.example.mvvmwithdaggerhilt.demo

import android.app.Application
import com.example.mvvmwithdaggerhilt.R
import javax.inject.Inject

interface DemoRepository{
    fun getGreetings():String
}

class TestRepository @Inject constructor(
    private val context:Application,
    private val demoClass: DemoClass
    ) : DemoRepository {

    override fun getGreetings(): String {
        return demoClass.getData() + " " + context.getString(R.string.app_name)
    }
}

class TestRepo2: DemoRepository {
    override fun getGreetings(): String {
        return "testRepo2"
    }

}
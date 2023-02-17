package com.example.mvvmwithdaggerhilt.utils

import android.app.Application
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class HelperTest {
    lateinit var helper: Helper
    @Before
    fun beforeRun(){
        helper = Helper()
        println("Before run")
    }

    @After
    fun afterRun(){
        println("After run")
    }
    @Test
    fun isPalindrome() {
        val result = helper.isPalindrome("hello")
        Assert.assertEquals(false, result)
        println("Middle of run")
    }

    @Test
    fun isPalindrome_result_is_true() {
        val result = helper.isPalindrome("level")
        Assert.assertEquals(true, result)
        println("Middle of run")
    }
}
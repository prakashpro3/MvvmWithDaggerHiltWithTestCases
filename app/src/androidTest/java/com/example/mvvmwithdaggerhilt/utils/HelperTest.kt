package com.example.mvvmwithdaggerhilt.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Test


internal class HelperTest {

    @Test
    fun getStrings() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val helper = Helper()
        val result = helper.getStrings(context)
        Assert.assertEquals("MvvmWithDaggerHilt", result)
    }

    @Test
    fun getStrings_with_no_match() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val helper = Helper()
        val result = helper.getStrings(context)
        Assert.assertEquals("", result)
    }
}
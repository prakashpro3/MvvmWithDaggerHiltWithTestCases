package com.example.mvvmwithdaggerhilt.demo

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.mvvmwithdaggerhilt.utils.Utils.setLog

class ActivityObserver2:DefaultLifecycleObserver {
    private val tag = "ActivityObserver2"

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        setLog("LifecycleObserver", "$tag onCreate")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        setLog("LifecycleObserver", "$tag onStart")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        setLog("LifecycleObserver", "$tag onResume")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        setLog("LifecycleObserver", "$tag onPause")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        setLog("LifecycleObserver", "$tag onStop")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        setLog("LifecycleObserver", "$tag onDestroy")
    }
}
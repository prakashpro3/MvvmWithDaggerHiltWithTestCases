package com.example.mvvmwithdaggerhilt.demo

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.mvvmwithdaggerhilt.utils.Utils.setLog

class ActivityObserver3:LifecycleEventObserver {
    private val tag = "ActivityObserver3"
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
       setLog("LifecycleObserver", "$tag ${source.lifecycle.currentState.name} ${event.targetState.name}")
    }
}
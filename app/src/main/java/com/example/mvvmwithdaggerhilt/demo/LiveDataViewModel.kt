package com.example.mvvmwithdaggerhilt.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveDataViewModel @Inject constructor():ViewModel() {
    private var greetingObject = MutableLiveData<String>("Welcome")

    val getGreeting:LiveData<String>
    get() = greetingObject

    private var age = 0
    private val demoModelObject = MutableLiveData<DemoModel2>()

    val getDemoModel2:LiveData<DemoModel2>
    get() = demoModelObject


    fun setGreeting(){
        viewModelScope.launch {
            age++
            greetingObject.value = "Welcome pro - $age"
            delay(2000)
            val demoModel = DemoModel2("Pro", age)
            demoModelObject.value = demoModel
        }

    }
}
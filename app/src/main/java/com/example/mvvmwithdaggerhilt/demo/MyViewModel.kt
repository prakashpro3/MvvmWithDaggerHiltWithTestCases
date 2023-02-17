package com.example.mvvmwithdaggerhilt.demo

import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(val repo: DemoRepository): ViewModel() {
    var count = ObservableInt()
    val demoModel = DemoModel()
    fun getGreetings(): String {
        return repo.getGreetings()
    }

    fun incrementCount() {
        count.set(count.get()+1)
    }

    fun setDemoModel(){
        demoModel.name.set("Prakash")
        demoModel.age.set(count.get()+1)
    }
}
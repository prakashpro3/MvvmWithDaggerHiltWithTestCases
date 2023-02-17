package com.example.mvvmwithdaggerhilt.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class StateFlowViewModel: ViewModel() {
    private val _count = MutableStateFlow(Int)
    val getCount = _count.asStateFlow()
    val countdown = flow {
        val startingValue = 0
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue < 5){
            delay(1000)
            currentValue++
            emit(currentValue)
        }
    }

    val flow1 = (1..5).asFlow()
    var num = 0
    fun concateCount(count:Int): Flow<Int> {
        val flow2 = flow {
            num +=count
            delay(2000)
            emit(num)
        }
        return flow2
    }

    private var _countNum = MutableStateFlow(0)
    val countNum = _countNum.asStateFlow()
    fun increement(){
        if (_countNum.value < 5){
            _countNum.value++
        }else{
            _countNum.value = _countNum.value
        }
    }

    private var _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun increase(value:Int){
        viewModelScope.launch {
            _sharedFlow.emit(value)
        }
    }
}
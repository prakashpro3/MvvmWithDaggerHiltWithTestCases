package com.example.mvvmwithdaggerhilt.demo

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

class DemoModel{
    var name = ObservableField<String>()
    var age = ObservableInt()
}


package com.example.mvvmwithdaggerhilt.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.mvvmwithdaggerhilt.R
import com.example.mvvmwithdaggerhilt.databinding.ActivitySecondBinding
import kotlinx.coroutines.launch

class SecondActivity : AppCompatActivity() {

    private val liveDataViewModel: LiveDataViewModel by viewModels()
    lateinit var binding:ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)

        liveDataViewModel.getGreeting.observe(this) {
            binding.greet = it
        }

        liveDataViewModel.getDemoModel2.observe(this){
            binding.demo = it
        }

        binding.btnGreet.setOnClickListener {
            lifecycleScope.launch{
                liveDataViewModel.setGreeting()
            }

        }
        binding.btnNextActivity.setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }

    }
}
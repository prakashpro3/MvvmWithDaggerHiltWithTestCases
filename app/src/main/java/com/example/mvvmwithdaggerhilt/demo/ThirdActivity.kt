package com.example.mvvmwithdaggerhilt.demo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mvvmwithdaggerhilt.R
import com.example.mvvmwithdaggerhilt.databinding.ActivityThirdBinding
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding:ActivityThirdBinding
    private val viewModel: StateFlowViewModel by viewModels()
    private val tag = ThirdActivity::class.java.name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_third)

        lifecycleScope.launch {
            /*viewModel.flow1.flatMapLatest {value ->
                binding.txtGreet.text = "1--"+value.toString()
                viewModel.concateCount(value)
            }.collect{
                binding.txtGreet.text = it.toString()
            }*/
            /*repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.countNum.collectLatest {
                    binding.txtGreet.text = it.toString()
                }
            }*/

        }

        collectLatestLifecycleFlow(viewModel.countNum){
            Log.d(tag, "StateFlowcountNum-$it")
            binding.greet = it.toString()
        }

        collectLatestLifecycleFlow(viewModel.sharedFlow){
            Log.d(tag, "SharedFlowcountNum-$it")
        }

        binding.btnGreet.setOnClickListener {
            viewModel.increement()
            viewModel.increase(5)
        }
    }

    private fun <T> Activity.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit){
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                flow.collectLatest(collect)
            }
        }
    }
}
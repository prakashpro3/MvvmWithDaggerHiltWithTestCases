package com.example.mvvmwithdaggerhilt.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.mvvmwithdaggerhilt.R
import com.example.mvvmwithdaggerhilt.utils.Utils.setLog
import com.example.mvvmwithdaggerhilt.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {
    val tag = "MainActivity"
    /*@Inject
    lateinit var repo:DemoRepository*/
    private val myViewModel: MyViewModel by viewModels()
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //myViewModel = ViewModelProvider(this)[MyViewModel::class.java]
        binding.btnIncrement.setOnClickListener(this)
        binding.btnNextActivity.setOnClickListener(this)
        //binding.txtGreet.text = repo.getGreetings()
        //binding.txtGreet.text = myViewModel.getGreetings()
        lifecycle.addObserver(ActivityObserver())
        lifecycle.addObserver(ActivityObserver2())
        //lifecycle.addObserver(ActivityObserver3())
        setLog("LifecycleObserver", "$tag onCreate")
        //setCountInViewModel()
        //setCountInAtivity()
    }

    private fun setCountInViewModel(){
        binding.txtGreet.text = "ViewModel-"+myViewModel.count.toString()
    }

    var countInActivity = 0
    private fun setCountInAtivity(){
        binding.txtGreet.text = "Activity-"+countInActivity.toString()
    }

    fun incrementCountInActivity() {
        countInActivity++
    }

    override fun onRestart() {
        super.onRestart()
        setLog("LifecycleObserver", "$tag onRestart")
    }

    override fun onStart() {
        super.onStart()
        setLog("LifecycleObserver", "$tag onStart")
    }

    override fun onResume() {
        super.onResume()
        setLog("LifecycleObserver", "$tag onResume")
    }

    override fun onPause() {
        super.onPause()
        setLog("LifecycleObserver", "$tag onPause")
    }

    override fun onStop() {
        super.onStop()
        setLog("LifecycleObserver", "$tag onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        setLog("LifecycleObserver", "$tag onDestroy")
    }

    override fun onClick(v: View?) {
        if (v == binding.btnIncrement){
            myViewModel.incrementCount()
            binding.txt = myViewModel.count.get().toString()
            myViewModel.setDemoModel()
            binding.demoModel = myViewModel.demoModel
            //setCountInViewModel()

            /*incrementCountInActivity()
            setCountInAtivity()*/
        }else if (v == binding.btnNextActivity){
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}
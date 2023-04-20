package com.example.mvvmwithdaggerhilt.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.mvvmwithdaggerhilt.R
import com.example.mvvmwithdaggerhilt.databinding.ActivityLearningDemoBinding
import com.example.mvvmwithdaggerhilt.ui.activities.HomeActivity
import com.example.mvvmwithdaggerhilt.utils.Constant.AppName
import com.example.mvvmwithdaggerhilt.utils.DataStorePreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LearningDemoActivity : AppCompatActivity() {
    lateinit var binding: ActivityLearningDemoBinding
    val dataStore by preferencesDataStore(AppName)
    lateinit var dataStorePreference:DataStorePreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_learning_demo)
        dataStorePreference = DataStorePreference(this)
        Log.d("dataStorePreference", "onCreate: $dataStorePreference")
        setObserver()

        binding.btnSave.setOnClickListener {
            val keyName = binding.edtWriteKey.text.toString()
            val value = binding.edtWriteValue.text.toString()
            lifecycleScope.launch {
                /*val key = stringPreferencesKey(keyName)
                dataStore.edit {
                    it[key] = value
                }*/
                dataStorePreference.setData(keyName, value)
                binding.edtWriteKey.setText("")
                binding.edtWriteValue.setText("")
            }
            //startActivity(Intent(this, HomeActivity::class.java))
        }

        binding.btnFatch.setOnClickListener {
            val keyName = binding.edtReadKey.text.toString()
            lifecycleScope.launch {
                /*val key = stringPreferencesKey(keyName)
                val result = dataStore.data.first()[key]*/
                val result = dataStorePreference.getData(keyName)
                binding.txtResult.text = result?:"No result found"
            }
        }
    }

    private fun setObserver(){
        lifecycleScope.launch {
            dataStorePreference.dataObserve.collect{
                binding.txtLiveResult.text = it
            }
        }
    }
}
package com.example.mvvmwithdaggerhilt.utils

import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mvvmwithdaggerhilt.utils.Constant.AppName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStorePreference private constructor(val context: Context) {
    val Context.dataStore by preferencesDataStore(AppName)
    var lastStoredKey = stringPreferencesKey("")

    companion object{
        @Volatile
        private lateinit var INSTANCE:DataStorePreference
        operator fun invoke(context: Context):DataStorePreference{
            if (!::INSTANCE.isInitialized) {
                synchronized(this){
                    if (!::INSTANCE.isInitialized) {
                        INSTANCE = DataStorePreference(context)
                    }
                }
            }

            return INSTANCE
        }
    }

    suspend fun setData(keyName:String, value:String){
        val key = stringPreferencesKey(keyName)
        lastStoredKey = key
        context.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun getData(keyName: String): String? {
        val key = stringPreferencesKey(keyName)
        val result = context.dataStore.data.first()
        return result[key]
    }

    val dataObserve: Flow<String> = context.dataStore.data.map {
        it[lastStoredKey] ?:""
    }

    suspend inline fun <reified T> storeValue(key: Preferences.Key<T>, value: Any) {
        context.dataStore.edit {
            it[key] = value as T
        }
    }

    suspend fun <T> readValue(
        key: Preferences.Key<T>
    ):T? {
        val result = context.dataStore.data.first()
        return result[key]
    }
}
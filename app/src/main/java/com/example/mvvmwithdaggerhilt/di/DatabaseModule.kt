package com.example.mvvmwithdaggerhilt.di

import android.content.Context
import com.example.mvvmwithdaggerhilt.api.UserDao
import com.example.mvvmwithdaggerhilt.api.UserDataListDao
import com.example.mvvmwithdaggerhilt.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase):UserDao{
        return appDatabase.userDao()
    }

    @Singleton
    @Provides
    fun provideUserDataListDao(appDatabase: AppDatabase):UserDataListDao{
        return appDatabase.userDataListDao()
    }
}
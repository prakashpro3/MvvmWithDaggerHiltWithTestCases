package com.example.mvvmwithdaggerhilt.di

import android.content.Context
import androidx.room.Room
import com.example.mvvmwithdaggerhilt.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(components = [SingletonComponent::class], replaces = [DatabaseModule::class])
@Module
class TestDatabaseModule {

    @Singleton
    @Provides
    fun provideTestDatabase(@ApplicationContext context: Context):AppDatabase{
        return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
    }
}
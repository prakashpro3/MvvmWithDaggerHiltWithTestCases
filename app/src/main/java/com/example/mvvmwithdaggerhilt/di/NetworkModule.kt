package com.example.mvvmwithdaggerhilt.di

import com.example.mvvmwithdaggerhilt.api.ListOfDataApi
import com.example.mvvmwithdaggerhilt.api.UserApi
import com.example.mvvmwithdaggerhilt.utils.Constant.baseUrlUserAuthApi
import com.example.mvvmwithdaggerhilt.utils.Constant.baseUrlUserDataApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideGsonConverterFactory():GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    @Named("authApiUrl")
    fun provideAuthUrlRetrofitInstance(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrlUserAuthApi)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    @Named("listApiUrl")
    fun provideListUrlRetrofitInstance(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrlUserDataApi)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideUserApi(@Named("authApiUrl") retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideListOfDataApi(@Named("listApiUrl") retrofit: Retrofit):ListOfDataApi{
        return retrofit.create(ListOfDataApi::class.java)
    }
}
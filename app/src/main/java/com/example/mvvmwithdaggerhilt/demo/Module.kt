package com.example.mvvmwithdaggerhilt.demo

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class Module {
    /*@Provides
    @Named("test1")
    fun providesDemoRepository():DemoRepository{
        return TestRepo2()
    }

    @Provides
    @Named("test2")
    fun providesDemoRepository2(testRepository: TestRepository):DemoRepository{
        return testRepository
    }*/

    @Binds
    abstract fun provideDemoRepo(testRepository: TestRepository): DemoRepository
}
package com.example.mvvmwithdaggerhilt.ui.viewmodel

import android.text.TextUtils
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import app.cash.turbine.test
import com.example.mvvmwithdaggerhilt.api.ListOfDataApi
import com.example.mvvmwithdaggerhilt.api.UserDao
import com.example.mvvmwithdaggerhilt.api.UserDataListDao
import com.example.mvvmwithdaggerhilt.data.database.AppDatabase
import com.example.mvvmwithdaggerhilt.data.repositories.UserRepository
import com.example.mvvmwithdaggerhilt.ui.model.Data
import com.example.mvvmwithdaggerhilt.ui.model.UserListResponseModel
import com.example.mvvmwithdaggerhilt.utils.NetworkResult
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import okhttp3.MediaType
import okhttp3.ResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.shouldBeEqualTo
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


internal class UserViewModelTest {


    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Mock
    lateinit var listOfDataApi: ListOfDataApi


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun test_getUsers() = runTest(UnconfinedTestDispatcher()) {
        //Mockito.doReturn((emptyList<UserListResponseModel>())).`when`(repository).getUserList()
        //Mockito.`when`(repository.getUserList).thenReturn(NetworkResult.Success(emptyList()))
        /*val viewModel = UserViewModel(repository)
        viewModel.getUserList.test {
            //Assert.assertEquals(NetworkResult.Success(emptyList<UserListResponseModel>()), awaitItem())
            //cancelAndIgnoreRemainingEvents()
        }
        Mockito.verify(repository).getUserList*/
    }




    @Test
    fun userDataWithMockApiResponse() = runTest(UnconfinedTestDispatcher()){
        val userListResponseModel = UserListResponseModel(emptyList<Data>(), 1, 20)
        Mockito.`when`(listOfDataApi.getUserList(1, 20)).thenReturn(Response.success(userListResponseModel))
        /*Mockito.`when`(listOfDataApi.getUserList(1, 20)).thenReturn(Response.error(401, ResponseBody.create(
            MediaType.parse(""), "unAuthorized")))*/

        val sut = UserRepository(listOfDataApi, null)
        val viewModel = UserViewModel(sut)
        val sharedFlow = MutableSharedFlow<String>()
        val job = launch(start = CoroutineStart.LAZY) {
            //sharedFlow.emit("A")
            //result.emit(NetworkResult.Success(any()))
            viewModel.getUserList(1, 20)
        }

        /*sharedFlow.test {
            Assert.assertEquals("A",expectMostRecentItem())
        }*/
        viewModel.getUserList.test {
            job.start()
            val result = expectMostRecentItem()
            Assert.assertEquals(true, result is NetworkResult.Success)
            Assert.assertEquals(20, result.data!!.totalPassengers)
            //Assert.assertEquals(true, result is NetworkResult.Error)
            //"Something went wrong" shouldBeEqualTo result.message
            cancelAndIgnoreRemainingEvents()
        }

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
package com.example.mvvmwithdaggerhilt

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.mvvmwithdaggerhilt.api.ListOfDataApi
import com.example.mvvmwithdaggerhilt.data.repositories.UserRepository
import com.example.mvvmwithdaggerhilt.ui.viewmodel.UserViewModel
import com.example.mvvmwithdaggerhilt.utils.NetworkResult
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.shouldBeEqualTo
import org.junit.*
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListOfDataApiTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var listOfDataApi: ListOfDataApi
    lateinit var mockWebServer:MockWebServer
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mockWebServer = MockWebServer()
        listOfDataApi = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create()).build().create(ListOfDataApi::class.java)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun userDataWithMockRetrofitApiSuccessResponse() = runTest(UnconfinedTestDispatcher()){
        val mockResponse = MockResponse()
        mockResponse.setBody("{\"totalPassengers\":17705,\"totalPages\":886,\"data\":[{\"_id\":\"63e5632a513ada69853f51f7\",\"name\":\"Fred Salazar\",\"trips\":200,\"airline\":[{\"id\":5,\"name\":\"Eva Air\",\"country\":\"Taiwan\",\"logo\":\"https://upload.wikimedia.org/wikipedia/en/thumb/e/ed/EVA_Air_logo.svg/250px-EVA_Air_logo.svg.png\",\"slogan\":\"Sharing the World, Flying Together\",\"head_quaters\":\"376, Hsin-Nan Rd., Sec. 1, Luzhu, Taoyuan City, Taiwan\",\"website\":\"www.evaair.com\",\"established\":\"1989\"}],\"__v\":0}]}")
        mockWebServer.enqueue(mockResponse)

        val resoponse = listOfDataApi.getUserList(1, 20)
        mockWebServer.takeRequest()

        Assert.assertEquals(true, resoponse.body().toString().isNotEmpty())
        17705 shouldBeEqualTo resoponse.body()?.totalPassengers
    }

    @Test
    fun userDataWithMockRetrofitApiErrorResponse() = runTest(UnconfinedTestDispatcher()){
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockResponse.setBody("Something went wrong")
        mockWebServer.enqueue(mockResponse)

        val resoponse = listOfDataApi.getUserList(1, 20)
        mockWebServer.takeRequest()

        Assert.assertEquals(false, resoponse.isSuccessful)
        404 shouldBeEqualTo resoponse.code()
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }
}
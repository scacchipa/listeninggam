package ar.com.westsoft.listening.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.MockedConstruction
import org.mockito.Mockito.mockConstruction
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ExternalApiTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var builderConstructor: MockedConstruction<Request.Builder>

    private val mockClient = mock<OkHttpClient>()
    private val mockRequest = mock<Request>()
    private val mockCall = mock<Call>()
    private val mockResponse = mock<Response>()
    private val mockResponseBody = mock<ResponseBody>()

    private val subject = ExternalApi(mockClient, testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Before
    fun setUp() {
        whenever(mockClient.newCall(mockRequest)).thenReturn(mockCall)
        whenever(mockCall.execute()).thenReturn(mockResponse)
        whenever(mockResponse.body()).thenReturn(mockResponseBody)
        whenever(mockResponseBody.string()).thenReturn("Response body")
    }

    @Test
    fun subjectDownloadFile_successfully() = runTest {
        whenever(mockResponse.body()).thenReturn(mockResponseBody)

        builderConstructor = mockConstruction(Request.Builder::class.java) { mock, setting ->
                whenever(mock.url("www.pablo.com.ar")).thenReturn(mock)
                whenever(mock.build()).thenReturn(mockRequest)
            }

        val actual = subject.downloadFile(url = "www.pablo.com.ar")
        val expect = "Response body"

        assertEquals(builderConstructor.constructed().size, 1)
        assertEquals(actual, expect)

        builderConstructor.close()
    }

    @Test
    fun subjectDownloadFile_returnNull() = runTest {
        whenever(mockResponse.body()).thenReturn(null)

        builderConstructor = mockConstruction(Request.Builder::class.java) { mock, setting ->
                whenever(mock.url("www.pablo.com.ar")).thenReturn(mock)
                whenever(mock.build()).thenReturn(mockRequest)
            }

        val actual = subject.downloadFile(url = "www.pablo.com.ar")

        assertEquals(builderConstructor.constructed().size, 1)
        assertNull(actual)

        builderConstructor.close()
    }

    @Test
    fun subjectDownloadFile_withOriginalRequestBuild() = runTest {
        whenever(mockResponse.body()).thenReturn(mockResponseBody)

        builderConstructor = mockConstruction(Request.Builder::class.java) { mock, setting ->
            whenever(mock.url("www.pablo.com.ar")).thenReturn(mock)
            whenever(mock.build()).thenReturn(mockRequest)
        }

        val actual = subject.downloadFile(url = "www.pablo.com.ar")
        val expect = "Response body"

        assertEquals(builderConstructor.constructed().size, 1)
        assertEquals(actual, expect)

        builderConstructor.close()
    }
}

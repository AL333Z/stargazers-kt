package com.al333z.stargazers.extension

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.al333z.stargazers.service.network.ResourceStatus
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Unconfined
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ScopedDeferredResponseTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private val context = Unconfined

    private val observer: Observer<ResourceStatus<String>> = mock()

    @Test
    fun toLiveData() {
        val liveData = MutableLiveData<ResourceStatus<String>>()
        liveData.observeForever(observer)

        CompletableDeferred<Response<String>>(Response.success("x")).scoped(scope, context)
            .toLiveData(liveData)

        verify(observer).onChanged(ResourceStatus.Loading)
        verify(observer).onChanged(ResourceStatus.Success("x"))
    }

    @Test
    fun toLiveDataWithHttpError() {
        val liveData = MutableLiveData<ResourceStatus<String>>()
        liveData.observeForever(observer)

        val err = Response.error<String>(
            500,
            ResponseBody.create(MediaType.parse("application/json"), "Unavailable")
        )

        CompletableDeferred<Response<String>>(err).scoped(scope, context)
            .toLiveData(liveData)

        verify(observer).onChanged(ResourceStatus.Loading)
        verify(observer).onChanged(isA<ResourceStatus.Error>())
    }

    @Test
    fun toLiveDataWithError() {
        val liveData = MutableLiveData<ResourceStatus<String>>()
        liveData.observeForever(observer)

        val deferred = scope.async<Response<String>>(
            context = context,
            start = CoroutineStart.LAZY,
            block = { throw RuntimeException("boom!") }
        )

        deferred
            .scoped(scope, context)
            .toLiveData(liveData)

        verify(observer).onChanged(ResourceStatus.Loading)
        verify(observer).onChanged(isA<ResourceStatus.Error>())
    }
}

package com.al333z.stargazers.extension

import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ScopedDeferredResponseTest {

//    @Rule
//    @JvmField
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
//    private val context = Unconfined
//
//    private val observer: Observer<ResourceStatus<String>> = mock()
//
//    @Test
//    fun toLiveData() {
//        val liveData = MutableLiveData<ResourceStatus<String>>()
//        liveData.observeForever(observer)
//
//        CompletableDeferred<Response<String>>(Response.success("x")).scoped(scope, context)
//            .toLiveData(liveData)
//
//        verify(observer).onChanged(ResourceStatus.Loading)
//        verify(observer).onChanged(ResourceStatus.Success("x"))
//    }
//
//    @Test
//    fun toLiveDataWithHttpError() {
//        val liveData = MutableLiveData<ResourceStatus<String>>()
//        liveData.observeForever(observer)
//
//        val err = Response.error<String>(
//            500,
//            ResponseBody.create(MediaType.parse("application/json"), "Unavailable")
//        )
//
//        CompletableDeferred<Response<String>>(err).scoped(scope, context)
//            .toLiveData(liveData)
//
//        verify(observer).onChanged(ResourceStatus.Loading)
//        verify(observer).onChanged(isA<ResourceStatus.Error>())
//    }
//
//    @Test
//    fun toLiveDataWithError() {
//        val liveData = MutableLiveData<ResourceStatus<String>>()
//        liveData.observeForever(observer)
//
//        val deferred = scope.async<Response<String>>(
//            context = context,
//            start = CoroutineStart.LAZY,
//            block = { throw RuntimeException("boom!") }
//        )
//
//        deferred
//            .scoped(scope, context)
//            .toLiveData(liveData)
//
//        verify(observer).onChanged(ResourceStatus.Loading)
//        verify(observer).onChanged(isA<ResourceStatus.Error>())
//    }
}

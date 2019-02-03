package com.al333z.stargazers.extension

import androidx.lifecycle.MutableLiveData
import com.al333z.stargazers.service.network.ResourceStatus
import com.al333z.stargazers.service.network.awaitResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

fun <A : Any> Deferred<Response<A>>.scoped(
    scope: CoroutineScope,
    context: CoroutineContext
): ScopedDeferredResponse<A> {
    return ScopedDeferredResponse(this, scope, context)
}

class ScopedDeferredResponse<A : Any>(
    private val deferred: Deferred<Response<A>>,
    private val scope: CoroutineScope,
    private val context: CoroutineContext
) {
    fun toLiveData(liveData: MutableLiveData<ResourceStatus<A>>) {
        scope.launch(context) {
            try {
                liveData.value = ResourceStatus.Loading
                val response = deferred.awaitResult().getOrThrow()
                liveData.value = ResourceStatus.Success(response)
            } catch (e: HttpException) {
                liveData.value = ResourceStatus.Error("Http error: ${e.message} | code ${e.response().code()}", e)
            } catch (t: Throwable) {
                liveData.value = ResourceStatus.Error("Exception: ${t.message}", t)
            }
        }
    }
}

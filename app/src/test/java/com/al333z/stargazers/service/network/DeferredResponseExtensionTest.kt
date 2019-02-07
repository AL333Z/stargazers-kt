package com.al333z.stargazers.service.network

import com.al333z.stargazers.service.Stargazer
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import retrofit2.Response

class DeferredResponseExtensionTest {

//    @Test
//    fun happyPath() {
//        runBlocking {
//            val sut = CompletableDeferred(Response.success("x"))
//            assertEquals(Result.Ok("x"), sut.awaitResult())
//        }
//    }
//
//    @Test
//    fun emptyBody() {
//        runBlocking {
//            val sut: Deferred<Response<Nothing>> = CompletableDeferred(Response.success(null))
//            val actual = sut.awaitResult()
//            when (actual) {
//                is Result.Exception -> Unit
//                else -> fail("Actual is $actual!")
//            }
//        }
//    }
//
//    @Test
//    fun httpError() {
//        runBlocking {
//            val response = Response.error<List<Stargazer>>(
//                500,
//                ResponseBody.create(MediaType.parse("application/json"), "Unavailable")
//            )
//            val sut = CompletableDeferred(response)
//            val actual = sut.awaitResult()
//            when (actual) {
//                is Result.Error -> Unit
//                else -> fail("Actual is $actual!")
//            }
//        }
//    }

}

package com.al333z.stargazers.service.network

import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class ResultTest {

    @Test
    fun getOrThrowOk() {
        val ok = Result.Ok("ok")
        assertEquals("ok", ok.getOrThrow())
    }

    @Test(expected = RuntimeException::class)
    fun getOrThrowException() {
        val exc = Result.Exception(RuntimeException("boom"))
        exc.getOrThrow()
    }

    @Test(expected = RuntimeException::class)
    fun getOrThrowError() {
        val error = Result.Error(
            HttpException(
                Response.error<String>(
                    500,
                    ResponseBody.create(MediaType.parse("application/json"), "Unavailable")
                )
            )
        )
        error.getOrThrow()
    }
}

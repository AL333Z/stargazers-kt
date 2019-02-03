package com.al333z.stargazers.service.network

import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> Deferred<Response<T>>.awaitResult(): Result<T> {
    return try {
        val response = await()
        if (response.isSuccessful) {
            val body = response.body()
            body?.let {
                Result.Ok(it)
            } ?: Result.Exception(Exception("Body is empty"))
        } else {
            Result.Error(HttpException(response))
        }
    } catch (e: Throwable) {
        Result.Exception(e)
    }
}

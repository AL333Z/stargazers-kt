package com.al333z.stargazers.service.network

import retrofit2.HttpException

sealed class Result<out T : Any> {
    // 200s
    data class Ok<out T : Any>(val value: T) : Result<T>()

    // Not 200s
    data class Error(val exception: HttpException) : Result<Nothing>()

    // Network or unexpected exception, empty or null body
    data class Exception(val exception: Throwable) : Result<Nothing>()

    fun getOrThrow(): T {
        return when (this) {
            is Result.Ok -> value
            is Result.Error -> throw  exception
            is Result.Exception -> throw exception
        }
    }
}

package com.al333z.stargazers.service.network

sealed class ResourceStatus<out A : Any> {
    data class Success<A : Any>(val data: A) : ResourceStatus<A>()
    data class Error(val msg: String, val t: Throwable) : ResourceStatus<Nothing>()
    object Loading : ResourceStatus<Nothing>()
}

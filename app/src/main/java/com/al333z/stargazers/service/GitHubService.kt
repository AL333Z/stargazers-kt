package com.al333z.stargazers.service

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface GitHubService {

    @GET("/repos/{owner}/{repo}/stargazers")
    fun getStargazersAsync(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("per_page") perPage: Int
    ): Deferred<Response<List<Stargazer>>>

    @GET
    fun getNextFromUrlAsync(@Url url: String): Deferred<Response<List<Stargazer>>>
}

sealed class NetworkState {
    object Loading : NetworkState()
    object Loaded : NetworkState()
    data class Failed(val message: String) : NetworkState ()
}

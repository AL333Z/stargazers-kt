package com.al333z.stargazers.service

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("/repos/{owner}/{repo}/stargazers")
    fun getStargazersAsync(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Deferred<Response<List<Stargazer>>>

}

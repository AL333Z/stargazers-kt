package com.al333z.stargazers.ui.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.al333z.stargazers.service.GitHubService
import com.al333z.stargazers.service.NetworkState
import com.al333z.stargazers.service.Stargazer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import retrofit2.Response

class StargazersPagedDataSource(
    private val owner: String?,
    private val repo: String?,
    private var gitHubService: GitHubService,
    private val coroutineScope: CoroutineScope
) : PageKeyedDataSource<String, Stargazer>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Stargazer>) {
        if (owner != null && repo != null) {
            loadFrom(
                { gitHubService.getStargazersAsync(owner, repo, params.requestedLoadSize) },
                { values: List<Stargazer>, next: String? -> callback.onResult(values, null, next) }
            )
        } else {
            callback.onResult(emptyList(), null, null)
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Stargazer>) {
        loadFrom(
            { gitHubService.getNextFromUrlAsync(params.key) },
            { values: List<Stargazer>, next: String? -> callback.onResult(values, next) }
        )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Stargazer>) {}

    private fun loadFrom(
        asyncCall: () -> Deferred<Response<List<Stargazer>>>,
        callback: (List<Stargazer>, String?) -> Unit
    ) {
        coroutineScope.launch(coroutineScope.coroutineContext) {
            networkState.postValue(NetworkState.Loading)
            val response = asyncCall().await()

            if (response.isSuccessful){
                val values = response.body() ?: emptyList()
                networkState.postValue(NetworkState.Loaded)

                val linkHeader = response.headers()["Link"]
                val links = linkHeader?.split(",")
                val next = links?.firstOrNull { it.endsWith("rel=\"next\"") }
                val nextUrl = next?.split(";")?.getOrNull(0)?.trim()?.drop(1)?.dropLast(1)

                callback(values, nextUrl)
            } else {
                networkState.postValue(NetworkState.Failed(response.message()))
            }
        }
    }
}

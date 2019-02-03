package com.al333z.stargazers.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.al333z.stargazers.extension.scoped
import com.al333z.stargazers.service.GitHubService
import com.al333z.stargazers.service.Stargazer
import com.al333z.stargazers.service.network.ResourceStatus
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class StargazersViewModel @Inject constructor(
    private val gitHubService: GitHubService,
    private val coroutineContext: CoroutineContext
) : ScopedViewModel() {

    private val _stargazers = MutableLiveData<ResourceStatus<List<Stargazer>>>()

    val stargazers: LiveData<ResourceStatus<List<Stargazer>>>
        get () = _stargazers

    fun getStargazers(owner: String, repo: String) {
        gitHubService.getStargazersAsync(owner, repo)
            .scoped(scope, coroutineContext)
            .toLiveData(_stargazers)
    }
}

package com.al333z.stargazers.ui.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.al333z.stargazers.service.GitHubService
import com.al333z.stargazers.service.Stargazer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StargazersPagedDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val gitHubService: GitHubService
) : DataSource.Factory<String, Stargazer>() {

    private var owner: String? = null
    private var repo: String? = null

    private val _dataSources = MutableLiveData<StargazersPagedDataSource>()
    val dataSources: LiveData<StargazersPagedDataSource> = _dataSources

    override fun create(): DataSource<String, Stargazer> {
        val ds = StargazersPagedDataSource(owner, repo, gitHubService, coroutineScope)

        coroutineScope.launch(Dispatchers.Main) {
            _dataSources.value = ds
        }
        return ds
    }

    fun createFor(owner: String, repo: String) {
        this.owner = owner
        this.repo = repo
        _dataSources.value?.invalidate()
    }

}

package com.al333z.stargazers.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.al333z.stargazers.service.GitHubService
import com.al333z.stargazers.service.NetworkState
import com.al333z.stargazers.service.Stargazer
import com.al333z.stargazers.ui.datasource.StargazersPagedDataSourceFactory
import javax.inject.Inject

class StargazersViewModel @Inject constructor(gitHubService: GitHubService) : ScopedViewModel() {

    private val pagedListConfig = PagedList.Config.Builder()
        .setPageSize(2)
        .setEnablePlaceholders(false)
        .build()

    private val dataSourceFactory = StargazersPagedDataSourceFactory(scope, gitHubService)

    val networkState: LiveData<NetworkState> =
        Transformations.switchMap(dataSourceFactory.dataSources) { it.networkState }

    val stargazers: LiveData<PagedList<Stargazer>> =
        LivePagedListBuilder<String, Stargazer>(dataSourceFactory, pagedListConfig).build()

    fun search(owner: String, repo: String) {
        dataSourceFactory.createFor(owner, repo)
    }
}

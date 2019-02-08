package com.al333z.stargazers.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.al333z.stargazers.service.GitHubService
import com.al333z.stargazers.service.NetworkState
import com.al333z.stargazers.service.Stargazer
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.ResponseBody.create
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class StargazersViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val service: GitHubService = mock()

    private val networkStateObserver: Observer<NetworkState> = mock()
    private val stargazersObserver: Observer<PagedList<Stargazer>> = mock()

    private lateinit var sut: StargazersViewModel

    private val results = listOf(
        Stargazer("http://jane.url", "Jane"),
        Stargazer("http://john.url", "John")
    )

    private val owner = "al333z"
    private val repo = "arrow"

    @Before
    fun setUp() {
        Dispatchers.setMain(Unconfined)
        sut = StargazersViewModel(service)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun happyPath() {

        whenever(service.getStargazersAsync(eq(owner), eq(repo), any()))
            .doReturn(
                CompletableDeferred(
                    Response.success(
                        results,
                        Headers.Builder()
                            .add("Link", "\"http://next.url\";rel=\"next\"")
                            .build()
                    )
                )
            )

        sut.stargazers.observeForever(stargazersObserver)
        sut.networkState.observeForever(networkStateObserver)

        sut.search(owner, repo)

        verify(stargazersObserver).onChanged(argWhere { it.isEmpty() })
        verify(networkStateObserver).onChanged(NetworkState.Loading)

        verify(stargazersObserver).onChanged(argWhere { it.containsAll(results) })
        verify(networkStateObserver).onChanged(NetworkState.Loaded)
    }

    @Test
    fun errorPath() {

        whenever(service.getStargazersAsync(eq(owner), eq(repo), any()))
            .doReturn(
                CompletableDeferred(
                    Response.error(
                        500,
                        create(MediaType.parse("application/json"), "Unavailable")
                    )
                )
            )

        sut.stargazers.observeForever(stargazersObserver)
        sut.networkState.observeForever(networkStateObserver)

        sut.search(owner, repo)

        verify(stargazersObserver, times(2)).onChanged(argWhere { it.isEmpty() })
        verify(networkStateObserver).onChanged(NetworkState.Loading)
        verify(networkStateObserver).onChanged(isA<NetworkState.Failed>())
    }

}

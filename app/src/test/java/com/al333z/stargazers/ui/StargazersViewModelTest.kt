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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.ResponseBody
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

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    private val service: GitHubService = mock()

    private val sut = StargazersViewModel(service)

    private val results = listOf(
        Stargazer("http://jane.url", "Jane"),
        Stargazer("http://john.url", "John")
    )

    private val networkStateObserver: Observer<NetworkState> = mock()
    private val stargazersObserver: Observer<PagedList<Stargazer>> = mock()

    @Test
    fun happyPath() {
        val owner = "al333z"
        val repo = "arrow"

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

        verify(networkStateObserver).onChanged(NetworkState.Loading)
        // ...
        verify(networkStateObserver).onChanged(NetworkState.Loaded)
    }

    @Test
    fun error() {
        val owner = "al333z"
        val repo = "arrow"
        whenever(service.getStargazersAsync(eq(owner), eq(repo), any()))
            .doReturn(
                CompletableDeferred(
                    Response.error(
                        500,
                        ResponseBody.create(MediaType.parse("application/json"), "Unavailable")
                    )
                )
            )

        sut.stargazers.observeForever(stargazersObserver)
        sut.networkState.observeForever(networkStateObserver)

        sut.search(owner, repo)

        verify(networkStateObserver).onChanged(NetworkState.Loading)
        // ...
        verify(networkStateObserver).onChanged(isA<NetworkState.Failed>())
    }

}

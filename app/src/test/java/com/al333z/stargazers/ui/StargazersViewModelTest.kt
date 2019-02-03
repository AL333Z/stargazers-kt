package com.al333z.stargazers.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.al333z.stargazers.service.GitHubService
import com.al333z.stargazers.service.Stargazer
import com.al333z.stargazers.service.network.ResourceStatus
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class StargazersViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val service: GitHubService = mock()

    private val sut = StargazersViewModel(service, Unconfined)

    private val results = listOf(
        Stargazer("", "Jane"),
        Stargazer("", "John")
    )

    private val observer: Observer<ResourceStatus<List<Stargazer>>> = mock()

    @Test
    fun happyPath() {
        whenever(service.getStargazersAsync(any(), any()))
            .doReturn(CompletableDeferred(Response.success(results)))

        sut.stargazers.observeForever(observer)

        sut.getStargazers("al333z", "arrow")

        verify(observer).onChanged(ResourceStatus.Loading)
        verify(observer).onChanged(ResourceStatus.Success(results))
    }

    @Test
    fun error() {
        val err = Response.error<List<Stargazer>>(
            500,
            ResponseBody.create(MediaType.parse("application/json"), "Unavailable")
        )

        whenever(service.getStargazersAsync(any(), any()))
            .doReturn(CompletableDeferred(err))

        sut.stargazers.observeForever(observer)

        sut.getStargazers("al333z", "arrow")

        verify(observer).onChanged(ResourceStatus.Loading)
        verify(observer).onChanged(isA<ResourceStatus.Error>())
    }
}

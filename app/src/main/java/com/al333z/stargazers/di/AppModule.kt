package com.al333z.stargazers.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@Module
internal class AppModule {
    @Provides
    fun provideMainCoroutineContext(): CoroutineContext = Dispatchers.Main
}

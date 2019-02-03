package com.al333z.stargazers.di

import com.al333z.stargazers.ui.StargazersFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ServiceModule::class,
        ViewModelModule::class]
)
interface AppComponent {
    fun inject(stargazerFragment: StargazersFragment)
}

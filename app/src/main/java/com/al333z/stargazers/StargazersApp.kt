package com.al333z.stargazers

import android.app.Application
import android.content.Context
import com.al333z.stargazers.di.AppComponent
import com.al333z.stargazers.di.DaggerAppComponent

class StargazersApp : Application() {

    companion object {
        fun getAppComponent(context: Context): AppComponent {
            val app = context.applicationContext as StargazersApp
            return app.appComponent
        }
    }

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }
}

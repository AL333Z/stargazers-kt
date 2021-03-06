package com.al333z.stargazers.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.al333z.stargazers.R

class StargazersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stargazers_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, StargazersFragment.newInstance())
                .commitNow()
        }
    }

}

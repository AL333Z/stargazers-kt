package com.al333z.stargazers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.al333z.stargazers.R

class StargazersFragment : Fragment() {

    companion object {
        fun newInstance() = StargazersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.stargazers_fragment, container, false)
    }

}

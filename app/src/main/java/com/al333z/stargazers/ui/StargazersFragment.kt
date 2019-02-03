package com.al333z.stargazers.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.al333z.stargazers.R
import com.al333z.stargazers.StargazersApp
import com.al333z.stargazers.service.Stargazer
import com.al333z.stargazers.service.network.ResourceStatus
import com.al333z.stargazers.ui.item.StargazersItemAdapter
import kotlinx.android.synthetic.main.stargazers_fragment.*
import javax.inject.Inject

class StargazersFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = StargazersFragment()
    }

    override fun onAttach(context: Context) {
        StargazersApp.getAppComponent(context).inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = StargazersItemAdapter()

        recycler_view.hasFixedSize()
        recycler_view.adapter = adapter

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(StargazersViewModel::class.java)
        viewModel.stargazers.observe(this, Observer { handleStatus(it, adapter) })

        search_button.setOnClickListener {
            hideKeyboard()
            viewModel.getStargazers(owner_text.text.toString(), repo_text.text.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.stargazers_fragment, container, false)
    }

    private fun handleStatus(status: ResourceStatus<List<Stargazer>>, adapter: StargazersItemAdapter) {
        when (status) {
            is ResourceStatus.Success -> {
                progress_bar.visibility = GONE
                adapter.addItems(status.data)
            }
            is ResourceStatus.Error -> {
                progress_bar.visibility = GONE
                status.t.printStackTrace()
                Toast.makeText(context, status.msg, Toast.LENGTH_SHORT).show()
            }
            ResourceStatus.Loading -> {
                progress_bar.visibility = VISIBLE
                adapter.reset()
            }
        }
    }

}

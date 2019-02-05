package com.al333z.stargazers.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.al333z.stargazers.R
import com.al333z.stargazers.service.Stargazer

class StargazersItemAdapter : PagedListAdapter<Stargazer, ViewHolder>(StargazersItemAdapter.DiffCallback) {

    companion object {
        // TODO looks ridiculous.. but it's what it's
        val DiffCallback: DiffUtil.ItemCallback<Stargazer> = object : DiffUtil.ItemCallback<Stargazer>() {
            override fun areItemsTheSame(oldItem: Stargazer, newItem: Stargazer): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Stargazer, newItem: Stargazer): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.stargazer_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

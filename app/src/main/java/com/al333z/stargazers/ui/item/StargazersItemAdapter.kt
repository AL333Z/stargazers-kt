package com.al333z.stargazers.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.al333z.stargazers.R
import com.al333z.stargazers.service.Stargazer

class StargazersItemAdapter(private var data: MutableList<Stargazer> = mutableListOf()) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.stargazer_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    fun addItems(items: List<Stargazer>) {
        data.clear()
        data.addAll(items)
        this.notifyDataSetChanged()
    }

    fun reset() {
        data.clear()
        this.notifyDataSetChanged()
    }
}

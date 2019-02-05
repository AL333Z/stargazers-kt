package com.al333z.stargazers.ui.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.al333z.stargazers.R
import com.al333z.stargazers.service.Stargazer
import com.bumptech.glide.Glide

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val pic: ImageView = itemView.findViewById(R.id.pic)
    private val user: TextView = itemView.findViewById(R.id.user)

    fun bind(model: Stargazer?) {
        model?.let {
            Glide.with(itemView.context).load(it.avatarUrl).into(pic)
            user.text = it.userName
        } ?: run {
            Glide.with(itemView.context).clear(pic)
            user.text = ""
        }
    }
}

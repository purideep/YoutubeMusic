package com.youtube.music.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youtube.music.R
import com.youtube.music.model.PlayListModel
import com.youtube.music.model.PlaylistVideo

class PlaylistRecyclerAdapter(
    var list: MutableList<PlaylistVideo>,
    val listener: PlayListsAdapter.PlayListAdapterListener
) :
    RecyclerView.Adapter<PlaylistRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.thumbnail)

        init {
            itemView.setOnClickListener {
                listener.onVideoClicked(list[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaylistRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.playlist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistRecyclerAdapter.ViewHolder, position: Int) {
        Glide.with(holder.image).load(list[position].image).placeholder(R.drawable.poster)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
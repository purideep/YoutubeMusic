package com.youtube.music.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.youtube.music.R
import com.youtube.music.model.PlaylistModel

class PlaylistRecyclerAdapter (private var model: List<PlaylistModel>) : RecyclerView.Adapter<PlaylistRecyclerAdapter.ViewHolder>()  {
    class ViewHolder (itemView: View) :RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.playlist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistRecyclerAdapter.ViewHolder, position: Int) {
        model.get(position).getthumbnail()?.let { holder.image.setImageResource(it) }

    }

    override fun getItemCount(): Int {
        return model.size
    }
}
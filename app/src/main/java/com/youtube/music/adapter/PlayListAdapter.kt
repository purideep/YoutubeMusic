package com.youtube.music.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.youtube.music.R
import com.youtube.music.model.PlaylistVideo

class PlayListAdapter(val context: Context, var model: List<PlaylistVideo>) :
    RecyclerView.Adapter<PlayListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.thumbnail)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayListAdapter.ViewHolder, position: Int) {
        holder.image.setImageResource(R.drawable.poster)
    }

    override fun getItemCount(): Int {
        return model.size
    }
}
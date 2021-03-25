package com.youtube.music.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.youtube.music.R
import com.youtube.music.model.PlaylistModel

class ListRecyclerAdapter (private var model: List<PlaylistModel>) : RecyclerView.Adapter<ListRecyclerAdapter.ViewHolder>()  {
    class ViewHolder (itemView: View) :RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.video_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListRecyclerAdapter.ViewHolder, position: Int) {
        model.get(position).getthumbnail()?.let { holder.image.setImageResource(it) }

    }

    override fun getItemCount(): Int {
        return model.size
    }
}
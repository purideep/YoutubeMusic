package com.youtube.music.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.youtube.music.R
import com.youtube.music.model.PlayListModel
import com.youtube.music.model.PlaylistVideo
import kotlinx.android.synthetic.main.activity_main.*

class PlayListsAdapter(
    val context: Context,
    val list: List<PlayListModel>,
    val listener: PlayListAdapterListener
) : RecyclerView.Adapter<PlayListsAdapter.PlayListRow>() {

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    inner class PlayListRow(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title);
        val videoCount: TextView = itemView.findViewById(R.id.video_count);
        val viewAll: TextView = itemView.findViewById(R.id.view_all);
        val recyclerView: RecyclerView = itemView.findViewById(R.id.playlist_recycler)

        init {
            viewAll.setOnClickListener { listener.onViewAllClicked(list[adapterPosition]) }
        }
    }

    interface PlayListAdapterListener {
        fun onViewAllClicked(model: PlayListModel)
        fun onVideoClicked(model: PlaylistVideo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListRow {
        return PlayListRow(layoutInflater.inflate(R.layout.custom_row_main, parent, false))
    }

    override fun onBindViewHolder(holder: PlayListRow, position: Int) {
        val model = list.get(position)
        holder.title.setText(model.title)
        holder.videoCount.setText("Videos ${model.itemCount}")

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val adapter = PlaylistRecyclerAdapter(model.list, listener)
        holder.recyclerView.layoutManager = linearLayoutManager
        holder.recyclerView.adapter = adapter
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
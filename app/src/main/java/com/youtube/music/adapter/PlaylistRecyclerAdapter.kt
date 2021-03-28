package com.youtube.music.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youtube.music.R
import com.youtube.music.model.PlayListModel
import com.youtube.music.model.PlaylistVideo

class PlaylistRecyclerAdapter(
    val originalList: MutableList<PlaylistVideo>,
    var list: MutableList<PlaylistVideo>,
    val listener: PlayListsAdapter.PlayListAdapterListener
) :
    RecyclerView.Adapter<PlaylistRecyclerAdapter.ViewHolder>(), Filterable {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.thumbnail)
        val title: TextView = itemView.findViewById(R.id.title)

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
        holder.title.setText(list[position].title)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                Log.e("OrignialList",originalList.size.toString())
                val localList = mutableListOf<PlaylistVideo>()
                if (constraint.isNullOrBlank() || constraint.isEmpty()) {
                    localList.addAll(originalList)
                } else {
                    for (item in originalList) {
                        if (item.title.toLowerCase()
                                .contains(constraint.toString().toLowerCase())
                        ) {
                            localList.add(item)
                        }
                    }
                }
                val result = FilterResults()
                result.values = localList
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    list.clear()
                    list.addAll(results.values as List<PlaylistVideo>)
                    notifyDataSetChanged()
                }
            }

        }
    }
}
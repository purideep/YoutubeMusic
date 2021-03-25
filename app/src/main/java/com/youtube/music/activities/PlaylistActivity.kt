package com.youtube.music.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.youtube.music.R
import com.youtube.music.adapter.ListRecyclerAdapter
import com.youtube.music.model.PlaylistModel
import kotlinx.android.synthetic.main.activity_main.*

class PlaylistActivity : AppCompatActivity() {

    private val model = ArrayList<PlaylistModel>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: ListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        var image = PlaylistModel(1, R.drawable.poster, "Playlist one", 6)
        model.add(image)
        var image1 = PlaylistModel(1, R.drawable.poster, "Playlist one", 6)
        model.add(image1)
        var image2 = PlaylistModel(1, R.drawable.poster, "Playlist one", 6)
        model.add(image2)
        var image3 = PlaylistModel(1, R.drawable.poster, "Playlist one", 6)
        model.add(image3)
        var image4 = PlaylistModel(1, R.drawable.poster, "Playlist one", 6)
        model.add(image4)
        var image5 = PlaylistModel(1, R.drawable.poster, "Playlist one", 6)
        model.add(image5)

        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        adapter = ListRecyclerAdapter(model)
        playlist_recycler.layoutManager = linearLayoutManager
        playlist_recycler.adapter = adapter
    }
}
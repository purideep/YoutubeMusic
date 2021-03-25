package com.youtube.music

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.youtube.music.activities.PlaylistActivity
import com.youtube.music.adapter.PlaylistRecyclerAdapter
import com.youtube.music.model.PlaylistModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val model = ArrayList<PlaylistModel>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: PlaylistRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_all.setOnClickListener {
            val intent = Intent(this, PlaylistActivity::class.java)
            startActivity(intent)
        }

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
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        adapter = PlaylistRecyclerAdapter(model)
        playlist_recycler.layoutManager = linearLayoutManager
        playlist_recycler.adapter = adapter

        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        adapter = PlaylistRecyclerAdapter(model)
        playlist_recycler1.layoutManager = linearLayoutManager
        playlist_recycler1.adapter = adapter

        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        adapter = PlaylistRecyclerAdapter(model)
        playlist_recycler2.layoutManager = linearLayoutManager
        playlist_recycler2.adapter = adapter


    }

}
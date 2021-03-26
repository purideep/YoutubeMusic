package com.youtube.music.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.youtube.music.R
import com.youtube.music.adapter.PlaylistRecyclerAdapter
import com.youtube.music.model.PlayListModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val model = ArrayList<PlayListModel>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: PlaylistRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_all.setOnClickListener {
            val intent = Intent(this, PlaylistActivity::class.java)
            startActivity(intent)
        }

        for (i in 0 until 10) {
            val image = PlayListModel("${i}", "Playlist ${i}", "5", "", i + 1, "2020")
            model.add(image)
        }

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
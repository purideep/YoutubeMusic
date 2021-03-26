package com.youtube.music.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.youtube.music.R
import com.youtube.music.adapter.ListRecyclerAdapter
import com.youtube.music.model.PlayListModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.playlist_recycler
import kotlinx.android.synthetic.main.activity_playlist.*

class PlaylistActivity : AppCompatActivity() {

    private val model = ArrayList<PlayListModel>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: ListRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        play_all.setOnClickListener {
            startActivity(Intent(this@PlaylistActivity, VideoPlayerActivity::class.java))
        }

        for (i in 0 until 10) {
            val image = PlayListModel("${i}", "Playlist ${i}", "5", "", i + 1, "2020")
            model.add(image)
        }

        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        adapter = ListRecyclerAdapter(model)
        playlist_recycler.layoutManager = linearLayoutManager
        playlist_recycler.adapter = adapter
    }
}
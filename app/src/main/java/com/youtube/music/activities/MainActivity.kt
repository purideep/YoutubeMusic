package com.youtube.music.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.youtube.music.R
import com.youtube.music.adapter.PlayListsAdapter
import com.youtube.music.adapter.PlaylistRecyclerAdapter
import com.youtube.music.model.PlayListModel
import com.youtube.music.model.PlaylistVideo
import com.youtube.music.presenter.MainActivityPresenter
import com.youtube.music.presenter.MainActivityView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityView,
    PlayListsAdapter.PlayListAdapterListener {
    var adapter: PlayListsAdapter? = null
    var presenter: MainActivityPresenter? = null
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)

        presenter = MainActivityPresenter(this)
        Log.e("MainActivity", "Load called")
        presenter?.load()
        /*
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
        */

    }

    override fun onPlayListReady(list: List<PlayListModel>) {
        adapter = PlayListsAdapter(this, list, this)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
    }

    override fun onViewAllClicked(model: PlayListModel) {

    }

    override fun onVideoClicked(model: PlaylistVideo) {
        startActivity(Intent(this,VideoPlayerActivity::class.java).putExtra("data",model))
    }

}
package com.youtube.music.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.youtube.music.R
import com.youtube.music.adapter.PlayListsAdapter
import com.youtube.music.database.DatabaseHandler
import com.youtube.music.model.PlayListModel
import com.youtube.music.model.PlaylistVideo
import com.youtube.music.presenter.MainActivityPresenter
import com.youtube.music.presenter.MainActivityView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
    }

    override fun onPlayListReady(list: List<PlayListModel>) {
        adapter = PlayListsAdapter(this, list, this)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
    }

    override fun onViewAllClicked(model: PlayListModel) {
        startActivity(Intent(this, PlaylistActivity::class.java).putExtra("data", model))
    }

    override fun onVideoClicked(model: PlaylistVideo) {
        startActivity(Intent(this, VideoPlayerActivity::class.java).putExtra("data", model))
    }

}
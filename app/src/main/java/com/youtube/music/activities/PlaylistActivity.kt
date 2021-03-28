package com.youtube.music.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youtube.music.R
import com.youtube.music.adapter.PlayListAdapter
import com.youtube.music.adapter.PlayListsAdapter
import com.youtube.music.adapter.PlaylistRecyclerAdapter
import com.youtube.music.model.PlayListModel
import com.youtube.music.model.PlaylistVideo
import com.youtube.music.presenter.PlayListPresenter
import com.youtube.music.presenter.PlayListView

class PlaylistActivity : AppCompatActivity(), PlayListView,
    PlayListsAdapter.PlayListAdapterListener {

    var presenter: PlayListPresenter? = null
    var adapterPlay: PlaylistRecyclerAdapter? = null
    var recyclerView: RecyclerView? = null
    var playAll: View? = null
    var searchView: SearchView? = null
    var imageView: ImageView? = null
    var titleTV: TextView? = null
    var videoCount: TextView? = null
    var description: TextView? = null

//    var playListMainImage: ImageView? = null
//    var playListTitle: TextView? = null
//    var videoCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)
        setupFindViewById()
        setupListeners()
        val model = intent.getSerializableExtra("data")
        if (model is PlayListModel) {
            titleTV?.setText(model.title)
            Glide.with(this).load(model.image).into(imageView!!)
            videoCount?.setText("Videos ${model.itemCount}")
            description?.setText(model.description)
            presenter = PlayListPresenter(this, model)
            presenter?.load()

        }
    }

    private fun setupListeners() {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterPlay?.filter?.filter(newText)
                return false
            }
        })

        playAll?.setOnClickListener {
            if (presenter?.model != null) {
                onViewAllClicked(presenter!!.model)
            }
        }
    }

    private fun setupFindViewById() {
        recyclerView = findViewById(R.id.recycler_view)
        playAll = findViewById(R.id.play_all)
        searchView = findViewById(R.id.search_view)

        imageView = findViewById(R.id.thumbnail)
        titleTV = findViewById(R.id.title)
        videoCount = findViewById(R.id.video_count)
        description = findViewById(R.id.description)
    }

    override fun onDataAvailable(list: MutableList<PlaylistVideo>) {
        if (adapterPlay == null) {
            adapterPlay = PlaylistRecyclerAdapter(list.toMutableList(), list, this)
            recyclerView?.layoutManager = LinearLayoutManager(this)
            recyclerView?.adapter = adapterPlay
        } else {
            adapterPlay?.list?.addAll(list)
            adapterPlay?.notifyDataSetChanged()
        }
    }

    // this function is being utilized from this file only
    override fun onViewAllClicked(model: PlayListModel) {
        startActivity(Intent(this, VideoPlayerActivity::class.java).putExtra("data", model))
    }

    override fun onVideoClicked(model: PlaylistVideo) {
        startActivity(Intent(this, VideoPlayerActivity::class.java).putExtra("data", model))
    }
}
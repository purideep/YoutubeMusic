package com.youtube.music.presenter

import com.youtube.music.model.PlayListModel
import com.youtube.music.model.PlaylistVideo
import com.youtube.music.network.CallAddr
import com.youtube.music.network.JSONParser
import com.youtube.music.network.URLS
import com.youtube.music.util.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface PlayListView {
    fun onDataAvailable(list: MutableList<PlaylistVideo>)
}

class PlayListPresenter(val view: PlayListView, val model: PlayListModel) {
    val list = mutableListOf<PlaylistVideo>()
    val page = 1
    val pageCode = ""
    val nextPageCode = ""
    fun load() {
        GlobalScope.launch(Dispatchers.IO) {
            val map = hashMapOf<String, Any>()
            val headers = hashMapOf<String, Any>()
            headers["Accept"] = "application/json"
            headers["Authorization"] = "Bearer ${SharedPref.get<String>("oath")}"
            var url = URLS.playListVideos
            val videoListJSON = CallAddr(map, "${url}${model.id}", null, true, headers).execute()
            list.addAll(JSONParser.parsePlaylistVideos(videoListJSON))
            GlobalScope.launch(Dispatchers.Main) {
                view.onDataAvailable(list)
            }
        }
    }
}
package com.youtube.music.presenter

import android.util.Log
import com.youtube.music.model.PlayListModel
import com.youtube.music.network.CallAddr
import com.youtube.music.network.JSONParser
import com.youtube.music.network.URLS
import com.youtube.music.util.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

interface MainActivityView {
    fun onPlayListReady(list: List<PlayListModel>)
}

class MainActivityPresenter(val view: MainActivityView) {
    var list = mutableListOf<PlayListModel>()
    fun load() {
        GlobalScope.launch {
            val map = hashMapOf<String, Any>()
            val headers = hashMapOf<String, Any>()
            headers["Accept"] = "application/json"
            headers["Authorization"] = "Bearer ${SharedPref.get<String>("oath")}"
            val response = CallAddr(map, URLS.playListMeta, null, true, headers).execute()
            list.clear()
            list.addAll(JSONParser.parsePlayLists(response))
            list.forEach {
                var url = URLS.playListVideos
                url = url.replace("maxResults=25", "maxResults=5")
                val videoListJSON = CallAddr(map, "${url}${it.id}", null, true, headers).execute()
                it.list.addAll(JSONParser.parsePlaylistVideos(videoListJSON))
            }
            GlobalScope.launch(Dispatchers.Main) {
                view.onPlayListReady(list)
            }
        }
    }
}
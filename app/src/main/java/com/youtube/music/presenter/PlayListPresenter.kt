package com.youtube.music.presenter

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.youtube.music.model.PlayListModel
import com.youtube.music.model.PlaylistVideo
import com.youtube.music.network.CallAddr
import com.youtube.music.network.JSONParser
import com.youtube.music.network.URLS
import com.youtube.music.util.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

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
            val ownerId = Firebase.auth.currentUser.uid ?: ""
            val localList = SharedPref.dbHelper?.getPlayListVideos(ownerId, model.id)

            if (localList.isNullOrEmpty()) {
                val map = hashMapOf<String, Any>()
                val headers = hashMapOf<String, Any>()
                headers["Accept"] = "application/json"
                headers["Authorization"] = "Bearer ${SharedPref.get<String>("oath")}"

                var pageToken = "initial"
                do {
                    if (pageToken == "initial") {
                        map.remove("pageToken")
                    } else {
                        map["pageToken"] = pageToken
                    }
                    var url = URLS.playListVideos
                    val response =
                        CallAddr(map, "${url}${model.id}", null, true, headers).execute()
                    Log.e("Main", "executed")
                    val jsonObject = JSONObject(response)
                    if (jsonObject.has("nextPageToken")) {
                        pageToken = jsonObject.getString("nextPageToken")
                    } else {
                        pageToken = ""
                    }
                    list.addAll(JSONParser.parsePlaylistVideos(response))
                } while (pageToken != "")
                val success = SharedPref.dbHelper?.addPlayListVideos(ownerId, model.id, list)
                Log.e("success", "vv ${success}")
                GlobalScope.launch(Dispatchers.Main) {
                    view.onDataAvailable(list)
                }
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    list.addAll(localList)
                    view.onDataAvailable(list)
                }
            }
        }
    }
}
package com.youtube.music.presenter

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.youtube.music.model.PlayListModel
import com.youtube.music.network.CallAddr
import com.youtube.music.network.JSONParser
import com.youtube.music.network.URLS
import com.youtube.music.util.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

interface MainActivityView {
    fun onPlayListReady(list: List<PlayListModel>)
}

class MainActivityPresenter(val view: MainActivityView) {
    var list = mutableListOf<PlayListModel>()
    fun load() {
        GlobalScope.launch {
            val ownerId = Firebase.auth.currentUser?.uid ?: ""
            val localList = SharedPref.dbHelper?.getPlayLists(ownerId)
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
                    val response = CallAddr(map, URLS.playListMeta, null, true, headers).execute()
                    //Log.e("Main", response)
                    val jsonObject = JSONObject(response)
                    if (jsonObject.has("nextPageToken")) {
                        pageToken = jsonObject.getString("nextPageToken")
                    } else {
                        pageToken = ""
                    }
                    list.addAll(JSONParser.parsePlayLists(response))
                } while (pageToken != "")

                list.forEach {
                    var url = URLS.playListVideos
                    url = url.replace("maxResults=50", "maxResults=5")
                    val videoListJSON =
                        CallAddr(map, "${url}${it.id}", null, true, headers).execute()
                    it.list.addAll(JSONParser.parsePlaylistVideos(videoListJSON))
                }
                list.forEach {
                    SharedPref.dbHelper?.addPlayListModel(ownerId, it)
                }

                GlobalScope.launch(Dispatchers.Main) {
                    view.onPlayListReady(list)
                }
            } else {
                list.addAll(localList!!)
                GlobalScope.launch(Dispatchers.Main) {
                    view.onPlayListReady(list)
                }
            }
        }
    }
}
package com.youtube.music.network

import android.util.Log
import com.youtube.music.model.PlayListModel
import com.youtube.music.model.PlaylistVideo
import org.json.JSONObject

object JSONParser {
    val TAG = javaClass.simpleName
    fun parsePlayLists(response: String): MutableList<PlayListModel> {
        val list: MutableList<PlayListModel> = mutableListOf()
        try {
            val obj = JSONObject(response)
            val items = obj.getJSONArray("items")
            for (i in 0 until items.length()) {
                val o = items.getJSONObject(i)
                val snippet = o.getJSONObject("snippet")
                val m = PlayListModel()
                m.id = o.getString("id")
                m.title = snippet.getString("title")
                m.description = snippet.getString("description")
                m.channelId = snippet.getString("channelId")
                m.image = snippet.getJSONObject("thumbnails").getJSONObject("high").getString("url")
                m.itemCount = o.getJSONObject("contentDetails").getInt("itemCount")
                list.add(m)
            }

        } catch (e: Exception) {
            Log.e(TAG, Log.getStackTraceString(e))
        }
        return list
    }

    fun parsePlaylistVideos(response: String): MutableList<PlaylistVideo> {
        val list: MutableList<PlaylistVideo> = mutableListOf()
        try {
            val obj = JSONObject(response)
            val items = obj.getJSONArray("items")
            for (i in 0 until items.length()) {
                val o = items.getJSONObject(i)
                val snippet = o.getJSONObject("snippet")
                val m = PlaylistVideo()
                m.id = o.getString("id")
                m.channelId = snippet.getString("channelId")
                m.title = snippet.getString("title")
                m.description = snippet.getString("description")
                if (m.description.trim().isEmpty()) {
                    m.description = "No Description"
                }
                m.image = snippet.getJSONObject("thumbnails").getJSONObject("high").getString("url")
                val contentDetails = o.getJSONObject("contentDetails")
                m.videoId = contentDetails.getString("videoId")
                m.videoPublishedAt = contentDetails.getString("videoPublishedAt")

                list.add(m)
            }

        } catch (e: Exception) {
            Log.e(TAG, Log.getStackTraceString(e))
        }
        return list
    }
}
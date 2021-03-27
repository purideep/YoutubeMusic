package com.youtube.music.model

import java.io.Serializable


data class PlayListModel(
    var id: String = "",
    var title: String = "",
    var channelId: String = "",
    var image: String = "",
    var itemCount: Int = 0,
    //"publishedAt": "2019-02-06T18:51:56.000Z",
    var publishedAt: String = "",
    var list: MutableList<PlaylistVideo> = mutableListOf<PlaylistVideo>()
) : Serializable


/*

class PlaylistModel(id : Int?, thumbnail: Int?, title: String?, video_count: Int?) {
    private var id : Int
    private var thumbnail : Int
    private var title : String
    private var video_count : Int

    init {
        this.id = id!!
        this.thumbnail = thumbnail!!
        this.title = title!!
        this.video_count = video_count!!
    }

    fun getId(): Int? {
        return id
    }

    fun setId(name: Int?) {
        id = name!!
    }
    fun setthumbnail(name: Int?) {
        thumbnail = name!!
    }

    fun getthumbnail(): Int? {
        return thumbnail
    }
    fun settitle(name: String?) {
        title = name!!
    }

    fun gettitile(): String? {
        return title
    }
    fun setvideocount(name: Int?) {
        video_count = name!!
    }

    fun getvidocount(): Int? {
        return video_count
    }
}
*/

package com.youtube.music.model

import java.io.Serializable

data class PlaylistVideo(
    var id: String = "",
    var title: String = "",
    var description: String = "No Description",
    var channelId: String = "",
    var image: String = "",
    var channelTitle: String = "",
    //"publishedAt": "2019-02-06T18:51:56.000Z",
    var videoPublishedAt: String = "",
    var videoId: String = ""
) : Serializable
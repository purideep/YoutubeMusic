package com.youtube.music.model

import java.io.Serializable

data class VideoModel(
    var title: String = "",
    var image: String = "",
    var videoId: String = "",
    var channelId: String = ""
) : Serializable
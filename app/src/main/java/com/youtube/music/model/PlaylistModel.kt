package com.youtube.music.model

import java.io.Serializable

data class PlayListModel(
    var id: String = "",
    var title: String = "",
    var channelId: String = "",
    var image: String = "",
    var itemCount: Int = 0,
    var publishedAt: String = "",
    var description: String = "",
    var list: MutableList<PlaylistVideo> = mutableListOf<PlaylistVideo>()
) : Serializable
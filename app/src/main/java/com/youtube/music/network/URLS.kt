package com.youtube.music.network

object URLS {
    val generateOathUrl = "https://oauth2.googleapis.com/token"
    val playListMeta =
        "https://youtube.googleapis.com/youtube/v3/playlists?part=snippet%2CcontentDetails&mine=true&maxResults=50"
    val playListVideos =
        "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet%2CcontentDetails&maxResults=50&playlistId="
    var search =
        "https://youtube.googleapis.com/youtube/v3/search?part=part=snippet%2CcontentDetails&forMine=true&maxResults=50&q="
}
package com.youtube.music.network

object URLS {
    val generateOathUrl = "https://oauth2.googleapis.com/token"
    val playListMeta = "https://youtube.googleapis.com/youtube/v3/playlists?part=snippet%2CcontentDetails&mine=true"
    val playListVideos = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet%2CcontentDetails&maxResults=25&playlistId="
}
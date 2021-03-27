package com.youtube.music.network

enum class Services(val trackingString: String) {
    // do not change any value
    HOME("pages?slug[]=home-2"),
    ABOUT_US("pages?slug[]=about-us"),
    AUDIO("/audio/"),
    YOUTUBE_PLAYLISTS(""),
    PLAYSLISTS_VIDEOS(""),
    LOGIN_EMAIL("logmein"),
    FORGOT_PASSWORD("forgotpassword"),
    REGISTER("getregister"),
    CHECK_SUCCESS("")
}
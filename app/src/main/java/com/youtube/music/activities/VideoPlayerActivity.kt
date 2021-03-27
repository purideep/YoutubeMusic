package com.youtube.music.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.youtube.music.R
import com.youtube.music.model.PlaylistVideo

class VideoPlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    var player: YouTubePlayerView? = null
    var mPlayer: YouTubePlayer? = null
    var videoId: String = "aBTvO-VwAN4"
    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videoplay)
        player = findViewById(R.id.player)
        val data = intent.getSerializableExtra("data")
        if (data != null) {
            if (data is PlaylistVideo) {
                videoId = data.videoId
                player?.initialize("AIzaSyDLXWe0YWuArGyVm09b5qFtaVUcQZt8MnI", this)
            }
        }
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        Log.e(TAG, "onInitializationSuccess")
        mPlayer = p1
        //Enables automatic control of orientation
        mPlayer?.fullscreenControlFlags = YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION

        //Show full screen in landscape mode always
        mPlayer?.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE)

        //System controls will appear automatically
        mPlayer?.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI)

        if (!p2) {
            //player.cueVideo("9rLZYyMbJic");
            mPlayer?.loadVideo(videoId)
        } else {
            mPlayer?.play()
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        Log.e("Player", "failed")
        mPlayer = null
    }
}
package com.ideabinbd.videoplayer.exoplayer

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorInput
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.ideabinbd.videoplayer.R
import kotlinx.android.synthetic.main.activity_my_player.*

class MyPlayerActivity : AppCompatActivity() {
    lateinit var mySimplePlayer : SimpleExoPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_player)

        initializePlayer()
    }

    var playWhenReady= false
    var currentWindow=0;
    var playbackPosition :Long =0 ;

    private fun initializePlayer(){
        mySimplePlayer= ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(this),
                DefaultTrackSelector(),
                DefaultLoadControl()
        )

        video_player.player= mySimplePlayer;

        mySimplePlayer.playWhenReady= playWhenReady
        mySimplePlayer.seekTo(currentWindow,playbackPosition)

        val uri = Uri.parse(getString(R.string.localMedia_url_mp4))
        val mediasource = buildMediaSource(uri)

        mySimplePlayer.prepare(mediasource,true,false)
    }

    private fun buildMediaSource(uri: Uri?): MediaSource? {
        //use this for default single file play
        return ExtractorMediaSource.Factory(
                DefaultHttpDataSourceFactory(getString(R.string.app_agent)))
                .createMediaSource(uri)


    }

    private fun hideSystemUi() {
        video_player.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun releasePlayer() {
        if (mySimplePlayer != null) {
            playbackPosition = mySimplePlayer.getCurrentPosition()
            currentWindow = mySimplePlayer.getCurrentWindowIndex()
            playWhenReady = mySimplePlayer.getPlayWhenReady()
            mySimplePlayer.release()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT <= 23) {
            initializePlayer()
        }
    }
}

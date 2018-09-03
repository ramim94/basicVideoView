package com.ideabinbd.videoplayer

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_main.*
import com.ideabinbd.videoplayer.R.id.videoView



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var videoList= ArrayList<String>()
        videoList.add("s1.mp4")
        videoList.add("s2.mp4")
        videoList.add("s3.mp4")

        val listAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,videoList)

        video_list.adapter= listAdapter

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)



        video_list.setOnItemClickListener { parent, view, position, id ->
            when (position){
                0 ->{
                    videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.s1))
                    videoView.start()
                }
                1 ->{
                    videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.s2))
                    videoView.start()
                }
                2 ->{
                    videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.s3))
                    videoView.start()
                }
            }
        }
    }
}

package com.ideabinbd.videoplayer.exoplayer

import android.Manifest
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.zxing.Result
import com.ideabinbd.videoplayer.R
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.nabinbhandari.android.permissions.Permissions.check
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.util.ArrayList

class QRActivity : AppCompatActivity() ,ZXingScannerView.ResultHandler{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)
        val scannerView = ZXingScannerView(this)



        check(this,Manifest.permission.CAMERA,null, object : PermissionHandler() {
            override fun onGranted() {
                setContentView(scannerView)
                scannerView.setResultHandler(this@QRActivity)
                scannerView.startCamera()
            }
        }

        )


    }
    val TAG="qrScannerData"
    override fun handleResult(p0: Result?) {
        Log.d(TAG,p0!!.text)
        val contentUrl= p0.text

        sendContentURLToPlaybackActivity(contentUrl);
    }

    private fun sendContentURLToPlaybackActivity(contentUrl: String?) {
        val playbackIntent= Intent(this@QRActivity, MyPlayerActivity::class.java)
        playbackIntent.putExtra("contentUrl",contentUrl)
        this@QRActivity.finish()
        startActivity(playbackIntent)
    }
}

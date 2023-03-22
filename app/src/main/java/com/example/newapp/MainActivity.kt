package com.example.newapp

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible


class MainActivity : AppCompatActivity() {
    var mPlayer: MediaPlayer? = null
    var playButton: Button? = null
    var pauseButton: Button? = null
    var stopButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPlayer = MediaPlayer.create(this, R.raw.first)
        mPlayer!!.setOnCompletionListener { stopPlay() }
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)
        pauseButton?.setEnabled(false) // костыль
        stopButton?.setEnabled(false) // костыль
    }

    private fun stopPlay() {
        mPlayer!!.stop()
        pauseButton!!.isEnabled = false
        stopButton!!.isEnabled = false
        try {
            mPlayer!!.prepare()
            mPlayer!!.seekTo(0)
            playButton!!.isVisible = true
        } catch (t: Throwable) {
            Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun play(view: View?) {
        mPlayer!!.start()
        playButton!!.isVisible = false
        pauseButton!!.isVisible = true
        pauseButton!!.isEnabled = true
        stopButton!!.isEnabled = true
    }

    fun pause(view: View?) {
        mPlayer!!.pause()
        playButton!!.isVisible = true
        pauseButton!!.isVisible = false
        stopButton!!.isEnabled = true
    }

    fun stop(view: View?) {
        stopPlay()
    }

    public override fun onDestroy() {
        super.onDestroy()
        if (mPlayer!!.isPlaying) {
            stopPlay()
        }
    }
}
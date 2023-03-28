package com.example.newapp

import android.media.MediaPlayer
import android.media.AsyncPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var mPlayer: MediaPlayer? = null
    //var mPlayer1: MediaPlayer? = null пока не используется
    var playButton: ImageButton? = null
    var pauseButton: ImageButton? = null
    var stopButton: ImageButton? = null
    var previousButton: ImageButton? = null
    var nextButton: ImageButton? = null
    var stopIsActive: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)
        previousButton = findViewById(R.id.previousButton)
        nextButton = findViewById(R.id.nextButton)
        stopButton = findViewById(R.id.stopButton)
        pauseButton?.setEnabled(false) // костыль
        stopButton?.setEnabled(false) // костыль
        nextButton?.setEnabled(false) // костыль
        previousButton?.setEnabled(false) // костыль
    }

    private fun stopPlay() {
        stopIsActive = true
        mPlayer!!.stop()
        pauseButton!!.isEnabled = false
        stopButton!!.isEnabled = false
        nextButton!!.isEnabled = false
        previousButton!!.isEnabled = false
        try {
            mPlayer!!.prepare()
            mPlayer!!.seekTo(0)
            playButton!!.isVisible = true
        } catch (t: Throwable) {
            Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun chooseSong(){
        when ((1..3).random()) {
            1 -> mPlayer = MediaPlayer.create(this, R.raw.first)
            2 -> mPlayer = MediaPlayer.create(this, R.raw.second)
            3 -> mPlayer = MediaPlayer.create(this, R.raw.rain)
        }
    }

    fun playSettings(){
        stopIsActive = false
        mPlayer!!.start()
        playButton!!.isVisible = false
        pauseButton!!.isVisible = true
        pauseButton!!.isEnabled = true
        stopButton!!.isEnabled = true
        nextButton!!.isEnabled = true
        previousButton!!.isEnabled = true
        mPlayer!!.setOnCompletionListener { next() }
    }

    fun previous(){

    }

    fun next() {
        mPlayer!!.stop()
        chooseSong()
        playSettings()
        //mPlayer?.setNextMediaPlayer(mPlayer1) /////////////// ВАЖНО!!! Пока не используется
    }
    fun nextTrack(view: View?)
    {
        next()
    }

    fun play() {
        if(stopIsActive == true) chooseSong()
        playSettings()

    }
    fun playMusic(view: View?){
        play()
    }


    fun pause(view: View?) {
        stopIsActive = false
        mPlayer!!.pause()
        playButton!!.isVisible = true
        pauseButton!!.isVisible = false
        stopButton!!.isEnabled = true
        nextButton!!.isEnabled = true
        previousButton!!.isEnabled = true
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
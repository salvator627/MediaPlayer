package com.example.media

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var seekBar: SeekBar
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar = findViewById(R.id.sbMusic)
        handler = Handler(Looper.getMainLooper())
        val play = findViewById<FloatingActionButton>(R.id.fabPlay)
        play.setOnClickListener {
            if (mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(this,R.raw.music)
                initializeSeekbar()
            }
            mediaPlayer?.start()
        }

        val pause = findViewById<FloatingActionButton>(R.id.fabPause)
        pause.setOnClickListener {
            mediaPlayer?.pause()
        }

        val stop = findViewById<FloatingActionButton>(R.id.fabStop)
        stop.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
            handler.removeCallbacks(runnable)
            seekBar.progress = 0
        }

    }
    private fun initializeSeekbar(){
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
               if (fromUser)mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        val name = findViewById<TextView>(R.id.TvPlay)
        name.text = "Martin Garrix Ft JRM - These Are The Times"
        val time = findViewById<TextView>(R.id.tvTime)
        seekBar.max = mediaPlayer!!.duration
        runnable = Runnable {
            seekBar.progress = mediaPlayer!!.currentPosition

            val playedtime = mediaPlayer!!.currentPosition/1000


            val duration = mediaPlayer!!.duration/1000
            val duetime = duration - playedtime

            time.text = "$duetime sec"

            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)
    }
}
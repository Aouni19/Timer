package com.example.timer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var isRunning=false

    private var pausedOffsetMs = 0L
    var startTime = SystemClock.elapsedRealtime()
    private lateinit var minsTv: TextView
    private lateinit var secsTv: TextView
    private lateinit var hundTv: TextView


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        minsTv = findViewById(R.id.mins)
        secsTv = findViewById(R.id.secs)
        hundTv = findViewById(R.id.hund)

        val startBut= findViewById<Button>(R.id.strt)
        val pauseBut=findViewById<Button>(R.id.pause)
        val resetBut=findViewById<Button>(R.id.rst)

        startBut.setOnClickListener {
            if (!isRunning) {
                startTimer()
                sendServiceAction("START")
            }
        }

        pauseBut.setOnClickListener {
            if (isRunning) {
                handler.removeCallbacks(runnable)
                isRunning = false
                pausedOffsetMs = SystemClock.elapsedRealtime() - startTime
                sendServiceAction("PAUSE")
            }
        }

        resetBut.setOnClickListener {
            if (isRunning) {
                handler.removeCallbacks(runnable)
                isRunning = false
            }
            pausedOffsetMs = 0L
            minsTv.text = "00"
            secsTv.text = "00"
            hundTv.text = "00"
            sendServiceAction("RESET")
        }

    }





    private fun startTimer(){
        startTime = SystemClock.elapsedRealtime() - pausedOffsetMs
        handler=Handler(Looper.getMainLooper())
        runnable=object:Runnable{
            override fun run() {
                updateTimer()
                handler.postDelayed(this, 100)
            }
        }
        handler.post(runnable)
        isRunning=true
    }

    private fun updateTimer(){
        val elapsedMs=SystemClock.elapsedRealtime() - startTime
         val totalSecs=elapsedMs/1000

        val mins=totalSecs/60
        val secs=totalSecs%60
        val hund=(elapsedMs%1000)/10

        minsTv.text = String.format("%02d", mins)
        secsTv.text = String.format("%02d", secs)
        hundTv.text = String.format("%02d", hund)
    }

    private fun sendServiceAction(action: String) {
        val svc = Intent(this, TImerService::class.java).apply {
            putExtra("action", action)
        }
        ContextCompat.startForegroundService(this, svc)
    }


}
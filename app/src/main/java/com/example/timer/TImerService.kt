package com.example.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class TImerService:Service() {

    private lateinit var handlerThread: HandlerThread
    private lateinit var handler: Handler
    private var startTimeMs = 0L
    private var pausedOffsetMs = 0L

    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notifManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        handlerThread=HandlerThread("Timer")
        handlerThread.start()
        handler=Handler(handlerThread.looper)

        notifManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan = NotificationChannel(
                "TI", "Timer Channel", NotificationManager.IMPORTANCE_LOW
            )
            notifManager.createNotificationChannel(chan)
        }

        notificationBuilder = NotificationCompat.Builder(this, "TI")
            .setSmallIcon(R.drawable.timer_foreground)
            .setContentTitle("Timer running")
            .setOnlyAlertOnce(true)
            .setOngoing(true)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra("action")) {
            "START" -> {
                startTimeMs = SystemClock.elapsedRealtime() - pausedOffsetMs
                notificationBuilder.setContentText("00:00:00")
                startForeground(100, notificationBuilder.build())
                handler.post(tickRunnable)
            }
            "PAUSE" -> {
                handler.removeCallbacks(tickRunnable)
                pausedOffsetMs = SystemClock.elapsedRealtime() - startTimeMs
            }
            "RESET" -> {
                handler.removeCallbacks(tickRunnable)
                pausedOffsetMs = 0L
                notificationBuilder.setContentText("00:00:00")
                notifManager.notify(100, notificationBuilder.build())
            }
        }
        return START_STICKY
    }


    private val tickRunnable= object : Runnable {
        override fun run() {
            val elapsed=SystemClock.elapsedRealtime()-startTimeMs
            val seconds=elapsed/1000
            val mins=seconds/60
            val secs=seconds%60
            val hund=(elapsed%1000)/10

            val time=String.format("%02d:%02d:%02d", mins, secs, hund)

            notificationBuilder.setContentText(time)
            notifManager.notify(1, notificationBuilder.build())

            handler.postDelayed(this, 10L)

        }
    }


    override fun onDestroy() {
        handler.removeCallbacks(tickRunnable)
        super.onDestroy()
    }



    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
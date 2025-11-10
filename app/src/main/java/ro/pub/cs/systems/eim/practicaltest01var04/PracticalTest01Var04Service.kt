package ro.pub.cs.systems.eim.practicaltest01var04

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class PracticalTest01Var04Service : Service() {
    var processingThread: ProcessingThread? = null

    override fun onCreate() {
        super.onCreate()

        val CHANNEL_ID = "channel_practicaltest01var04"
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Practical Test 01 Var04 Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            //val notification: Notification? = Builder(this, CHANNEL_ID)
            .setContentTitle("Practical Test 01 Var 04")
            .setContentText("Service running")
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .build()

        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val name_service = intent.getStringExtra("name_main")
        val group_service = intent.getStringExtra("group_main")
        Log.d("SERVICE", "Started with: left=$name_service, right=$group_service")

        if (processingThread == null) {
            processingThread = ProcessingThread(
                this,
                name_service.toString(),
                group_service.toString()
            )
            processingThread!!.start()
        }

        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        processingThread?.stopThread()
        Log.d("SERVICE", "Service destroyed")
    }
}
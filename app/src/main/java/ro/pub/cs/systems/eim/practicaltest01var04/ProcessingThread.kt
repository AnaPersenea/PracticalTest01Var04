package ro.pub.cs.systems.eim.practicaltest01var04

import android.content.Context
import android.content.Intent
import android.os.Process
import android.util.Log

class ProcessingThread(
    private val context: Context,
    @Volatile var nameThread: String,
    @Volatile var groupThread: String
) : Thread() {
    private var isRunning = true

    private val actions = listOf(
        "ro.pub.cs.systems.eim.practicaltest01var04.ACTION_1",
        "ro.pub.cs.systems.eim.practicaltest01var04.ACTION_2",
        "ro.pub.cs.systems.eim.practicaltest01var04.ACTION_3"
    )

    override fun run() {
        Log.d(
            "[Processing Thread]",
            "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid()
        )
        while (isRunning) {
            sendMessage()
            sleep()
        }
        Log.d("[Processing Thread]", "Thread has stopped!")
    }

    private fun sendMessage() {
        val action = actions.random()
        val broadcastIntent = Intent(action)

        broadcastIntent.putExtra("nume_thread", nameThread)
        broadcastIntent.putExtra("grupa_thread", groupThread)

        context.sendBroadcast(broadcastIntent)
    }

    private fun sleep() {
        try {
            sleep(5000)
        } catch (interruptedException: InterruptedException) {
            interruptedException.printStackTrace()
        }
    }

    fun stopThread() {
        isRunning = false
    }
}
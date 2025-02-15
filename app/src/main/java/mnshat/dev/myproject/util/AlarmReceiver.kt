package mnshat.dev.myproject.util

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import mnshat.dev.myproject.R
import mnshat.dev.myproject.users.patient.main.presentaion.UserScreensActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        log("Alarm Triggered: Running Task!")
        sendNotification("qwertttt0",context)
        // Start the WorkManager task or run your code here
//        val workManager = WorkManager.getInstance(context)
//        val dailyWorkRequest = OneTimeWorkRequestBuilder<MyWorkManager>().build()
//        workManager.enqueue(dailyWorkRequest)
    }
    @SuppressLint("MissingPermission")
    fun sendNotification(text:String,context: Context){

        val intent = Intent(context, UserScreensActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, CALENDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("My Application")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(15 ,builder.build())

    }
}

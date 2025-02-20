package mnshat.dev.myproject.util

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mnshat.dev.myproject.R
import mnshat.dev.myproject.dataSource.room.AppDatabase
import mnshat.dev.myproject.users.patient.main.presentaion.UserScreensActivity
import java.util.Date

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        sendNotification(" لا تنسى القيام بمهام اليوم tretwergf" , context)

        val workManager = WorkManager.getInstance(context)
        val request = OneTimeWorkRequestBuilder<MyWorkManager>().build()
        workManager.enqueue(request)
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

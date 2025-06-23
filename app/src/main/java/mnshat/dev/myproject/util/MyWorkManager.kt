package mnshat.dev.myproject.util

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.prolificinteractive.materialcalendarview.CalendarDay
import mnshat.dev.myproject.R
import mnshat.dev.myproject.users.patient.main.presentaion.UserScreensActivity
import java.util.Date
import android.content.Context

class MyWorkManager(private val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        val today = CalendarDay.today()
        val database = AppDatabase.getDatabase(context = context)
        val days = database.dayDao().getAllDays()
        val list = mutableSetOf<CalendarDay>()

        days?.forEach { day ->
            val date = Date(day.day)
            val calendarDay = CalendarDay(date)
            list.add(calendarDay)
        }

        if (list.contains(today)){
            sendNotification("لا تنسى القيام بمهام اليوم" , context)
        }

        return Result.success()
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



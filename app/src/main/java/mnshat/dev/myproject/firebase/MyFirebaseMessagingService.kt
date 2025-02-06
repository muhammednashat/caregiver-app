package mnshat.dev.myproject.firebase

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import mnshat.dev.myproject.R
import mnshat.dev.myproject.users.patient.main.presentaion.UserScreensActivity
import mnshat.dev.myproject.util.ENCOURAGEMENT_CHANNEL_ID
import mnshat.dev.myproject.util.SplashActivity

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

    }



    @SuppressLint("MissingPermission")
    fun sendNotification(context: Context){
        val intent = Intent(this, UserScreensActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, ENCOURAGEMENT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_user)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

     NotificationManagerCompat.from(context).notify(14 ,builder.build())

    }


}
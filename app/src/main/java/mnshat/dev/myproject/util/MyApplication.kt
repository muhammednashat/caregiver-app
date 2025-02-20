package mnshat.dev.myproject.util

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.android.exoplayer2.util.NotificationUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.HiltAndroidApp
import mnshat.dev.myproject.R
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import java.util.Calendar

@HiltAndroidApp
class MyApplication: Application() {

    lateinit var sharedPreferences: SharedPreferencesManager

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = SharedPreferencesManager(applicationContext)
        scheduleDailyAlarm(applicationContext)
        createChannel(getString(R.string.encouragement_messages), ENCOURAGEMENT_CHANNEL_ID, "", NotificationUtil.IMPORTANCE_DEFAULT)

        createChannel(getString(R.string.activities_reminder), CALENDER_CHANNEL_ID, "", NotificationUtil.IMPORTANCE_DEFAULT)


    onUserDataChanged(sharedPreferences)
    if (sharedPreferences.getString(TYPE_OF_USER ) == CAREGIVER){
        observeUserDailyProgramStates(sharedPreferences.getString(ID_PARTNER))
    }else{
        observeUserDailyProgramStates(sharedPreferences.getString(USER_ID))
    }

    }


    private fun createChannel(
        name: String,
        channelId: String,
        descriptionText: String,
        importance: Int
    ) {
        val mChannel = NotificationChannel(channelId, name, importance)
        mChannel.description = descriptionText
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }

    private fun observeUserDailyProgramStates(userId: String) {
         FirebaseDatabase.getInstance()
         .getReference().child(DAILY_PROGRAM_STATES)
         .child(userId).addValueEventListener(object :ValueEventListener{
             override fun onDataChange(snapshot: DataSnapshot) {
               val currentDay = snapshot.getValue(CurrentDay::class.java)
               sharedPreferences.storeObject(CURRENT_DAY,currentDay)
              log(currentDay?.status?.day.toString())
             }

             override fun onCancelled(error: DatabaseError) {

             }

         })
    }

    private fun onUserDataChanged(sharedPreferences: SharedPreferencesManager) {
       val databaseRef = FirebaseDatabase.getInstance().getReference()
           .child(USER_PROFILES)
           .child(sharedPreferences.getString(USER_ID, ""))
           databaseRef.addValueEventListener(object:ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {
               val userProfile = snapshot.getValue(RegistrationData::class.java)
               log(userProfile?.permissions?.allowMoodTrackingDetails.toString())
               sharedPreferences.storeBoolean(PERMISSION_MOOD,userProfile?.permissions?.allowMoodTrackingDetails)
               sharedPreferences.storeBoolean(PERMISSION_MASSAGE,userProfile?.permissions?.allowPrivateMessages)
               sharedPreferences.storeBoolean(PERMISSION_POINTS,userProfile?.permissions?.allowDailyProgramDetails)
               sharedPreferences.storeBoolean(HAS_PARTNER,userProfile?.hasPartner ?: false)
               sharedPreferences.storeObject(USER_PROFILES,userProfile)
           }

           override fun onCancelled(error: DatabaseError) {
           }

       })

    }



    @SuppressLint("ScheduleExactAlarm")
    fun scheduleDailyAlarm(context: Context) {

        val schedulingTime = sharedPreferences.getInt(key = SCHEDULING_TIME, defaultValue = 7)
        println(schedulingTime)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 13) // 5 PM
            set(Calendar.MINUTE, 30 )
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }


}
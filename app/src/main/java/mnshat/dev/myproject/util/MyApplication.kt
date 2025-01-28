package mnshat.dev.myproject.util

import android.app.Application
import android.widget.NumberPicker.OnValueChangeListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.HiltAndroidApp
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.util.SharedPreferencesManager

@HiltAndroidApp
class MyApplication: Application() {

    lateinit var sharedPreferences: SharedPreferencesManager

    override fun onCreate() {
        super.onCreate()
    sharedPreferences = SharedPreferencesManager(applicationContext)
    onUserDataChanged(sharedPreferences)
    if (sharedPreferences.getString(TYPE_OF_USER ) == CAREGIVER){
        observeUserDailyProgramStates(sharedPreferences.getString(ID_PARTNER))
    }else{
        observeUserDailyProgramStates(sharedPreferences.getString(USER_ID))
    }

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
               sharedPreferences.storeBoolean(PERMISSION_MOOD,userProfile?.permissions?.allowMoodTrackingDetails)
               sharedPreferences.storeBoolean(PERMISSION_MASSAGE,userProfile?.permissions?.allowPrivateMessages)
               sharedPreferences.storeBoolean(PERMISSION_POINTS,userProfile?.permissions?.allowDailyProgramDetails)
               sharedPreferences.storeObject(USER_PROFILES,userProfile)
           }

           override fun onCancelled(error: DatabaseError) {
           }

       })

    }

}
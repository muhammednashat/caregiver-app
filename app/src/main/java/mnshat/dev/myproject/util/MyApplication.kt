package mnshat.dev.myproject.util

import android.app.Application
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.HiltAndroidApp
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.util.SharedPreferencesManager

@HiltAndroidApp
class MyApplication: Application() {

    lateinit var sharedPreferences: SharedPreferencesManager

    override fun onCreate() {
        super.onCreate()
    sharedPreferences = SharedPreferencesManager(applicationContext)
    onUserDataChanged(sharedPreferences)
    }

    private fun onUserDataChanged(sharedPreferences: SharedPreferencesManager) {
       val databaseRef = FirebaseDatabase.getInstance().getReference()
           .child(USER_PROFILES)
           .child(sharedPreferences.getString(USER_ID, ""))
           databaseRef.addValueEventListener(object:ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {
               val userProfile = snapshot.getValue(RegistrationData::class.java)
               sharedPreferences.storeObject(USER_PROFILES,userProfile)
              log(userProfile?.permissions?.allowPrivateMessages.toString())
           }

           override fun onCancelled(error: DatabaseError) {
           }

       })

    }

}
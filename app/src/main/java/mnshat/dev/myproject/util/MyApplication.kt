package mnshat.dev.myproject.util

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import mnshat.dev.myproject.util.SharedPreferencesManager

@HiltAndroidApp
class MyApplication: Application() {

    lateinit var sharedPreferences: SharedPreferencesManager

    override fun onCreate() {
        super.onCreate()
    sharedPreferences = SharedPreferencesManager(applicationContext)
    }

}
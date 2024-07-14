package mnshat.dev.myproject.util

import android.app.Application
import mnshat.dev.myproject.util.SharedPreferencesManager

class MyApplication: Application() {

    lateinit var sharedPreferences: SharedPreferencesManager

    override fun onCreate() {
        super.onCreate()
    sharedPreferences = SharedPreferencesManager(applicationContext)
    }

}
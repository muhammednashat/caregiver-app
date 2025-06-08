package mnshat.dev.myproject.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(context: Context) {

    private val PREFS_NAME = "MyAppPrefs"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun storeString(key: String, value: String?) {
        if (value != null) {
            val editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
        }
    }
    fun storeInt(key: String, value: Int?) {
        if (value != null) {
            val editor = sharedPreferences.edit()
            editor.putInt(key, value)
            editor.apply()
        }
    }

    fun storeLang(key: String, value: Long?) {
        if (value != null) {
            val editor = sharedPreferences.edit()
            editor.putLong(key, value)
            editor.apply()
        }
    }

    fun getLong(key: String, defaultValue: Long = 0L)=
        sharedPreferences.getLong(key, defaultValue)

    fun getString(key: String, defaultValue: String = "")= sharedPreferences.getString(key, defaultValue) ?: defaultValue

    fun getInt(key: String, defaultValue: Int = 0)=   sharedPreferences.getInt(key, defaultValue)

    fun storeBoolean(key: String, value: Boolean?) {
        if (value != null){
            val editor = sharedPreferences.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }


    fun clearData() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
    fun storeObject(key:String,obj: Any?) {
        if (obj != null) {
            val editor = sharedPreferences.edit()
            val  gson = Gson()
            val json: String = gson.toJson(obj)
            editor.putString(key, json)
            editor.apply()
        }
    }

    fun getObjectProfilePartner(key: String) :RegistrationData {
        val string = sharedPreferences.getString(key, null.toString())
        val gson = Gson()
        return gson.fromJson(string, RegistrationData::class.java)
    }



}

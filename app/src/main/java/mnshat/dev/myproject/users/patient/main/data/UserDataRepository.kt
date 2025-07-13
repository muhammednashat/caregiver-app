package mnshat.dev.myproject.users.patient.main.data

import com.google.gson.Gson
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.util.CURRENT_DAY
import mnshat.dev.myproject.util.IS_LOGGED
import mnshat.dev.myproject.util.IS_SECOND_TIME
import mnshat.dev.myproject.util.SharedPreferencesManager

class UserDataRepository (
    val sharedPreferences: SharedPreferencesManager,
){

    fun userProfile () = sharedPreferences.getUserProfile()


     fun getCurrentDayLocally(): CurrentDay {
        val string = sharedPreferences.getString(CURRENT_DAY, null.toString())
        val gson = Gson()
        return gson.fromJson(string, CurrentDay::class.java)
    }

    fun isLogged() = sharedPreferences.getBoolean(IS_LOGGED)
    fun updateLoggedStatus() { sharedPreferences.storeBoolean(IS_SECOND_TIME,true)  }


}
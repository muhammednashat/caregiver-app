package mnshat.dev.myproject.users.patient.dailyprogram

import android.app.Application
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager

class DailyProgramViewModel(
    private val sharedPreferences: SharedPreferencesManager,
    application: Application

) : BaseViewModel(
    sharedPreferences,
    application
) {

}
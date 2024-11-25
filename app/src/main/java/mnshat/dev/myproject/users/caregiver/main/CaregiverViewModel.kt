package mnshat.dev.myproject.users.caregiver.main

import android.app.Application
import mnshat.dev.myproject.base.BaseViewModel2
import mnshat.dev.myproject.util.SharedPreferencesManager

open class CaregiverViewModel(
    private val sharedPreferences: SharedPreferencesManager,
    application: Application
) : BaseViewModel2(
    sharedPreferences,
    application
) {


}


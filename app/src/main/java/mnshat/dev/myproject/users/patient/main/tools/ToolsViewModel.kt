package mnshat.dev.myproject.users.patient.main.tools

import android.app.Application
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager

class ToolsViewModel(private val sharedPreferences: SharedPreferencesManager,
                     application: Application
):BaseViewModel(sharedPreferences,application) {
}
package mnshat.dev.myproject.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.users.caregiver.tools.CaregiverToolsViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager


class CaregiverToolsViewModelFactory

    (

     private val sharedPreferences: SharedPreferencesManager,
     private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CaregiverToolsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CaregiverToolsViewModel(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
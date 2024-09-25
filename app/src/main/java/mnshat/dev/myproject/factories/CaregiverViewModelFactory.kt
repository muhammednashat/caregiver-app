package mnshat.dev.myproject.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.users.caregiver.main.CaregiverViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager


class CaregiverViewModelFactory

    (

     private val sharedPreferences: SharedPreferencesManager,
     private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CaregiverViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CaregiverViewModel(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
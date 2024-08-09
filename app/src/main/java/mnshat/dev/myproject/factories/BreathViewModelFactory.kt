package mnshat.dev.myproject.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.auth.AuthViewModel
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.users.patient.main.tools.breath.BreathViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager


class BreathViewModelFactory

    (

     private val sharedPreferences: SharedPreferencesManager,
     private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BreathViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BreathViewModel(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
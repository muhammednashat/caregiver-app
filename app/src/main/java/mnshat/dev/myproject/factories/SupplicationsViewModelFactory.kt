package mnshat.dev.myproject.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.users.patient.main.tools.supplications.SupplicationsViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager


class SupplicationsViewModelFactory
    (
    private val sharedPreferences: SharedPreferencesManager,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SupplicationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SupplicationsViewModel(sharedPreferences, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
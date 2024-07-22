package mnshat.dev.myproject.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.users.patient.main.tools.ToolsViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager


class ToolsViewModelFactory
    (
    private val sharedPreferences: SharedPreferencesManager,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToolsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToolsViewModel(sharedPreferences, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
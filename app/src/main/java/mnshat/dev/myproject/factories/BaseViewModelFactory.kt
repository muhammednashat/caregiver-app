package mnshat.dev.myproject.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.base.BaseViewModel2
import mnshat.dev.myproject.util.SharedPreferencesManager


class BaseViewModelFactory
    (private val sharedPreferences: SharedPreferencesManager,
     private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BaseViewModel2::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BaseViewModel2(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
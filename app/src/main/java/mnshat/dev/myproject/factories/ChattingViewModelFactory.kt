package mnshat.dev.myproject.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.auth.AuthViewModel
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.features.chatting.ChattingViewModel
import mnshat.dev.myproject.features.libraraycontent.LibraryViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager


class ChattingViewModelFactory

    (

     private val sharedPreferences: SharedPreferencesManager,
     private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChattingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChattingViewModel(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
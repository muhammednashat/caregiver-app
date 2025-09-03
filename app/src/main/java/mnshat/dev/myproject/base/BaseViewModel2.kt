package mnshat.dev.myproject.base

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel

import mnshat.dev.myproject.util.SharedPreferencesManager
open class BaseViewModel2(
    private val sharedPreferences: SharedPreferencesManager,
   private val application: Application
) : AndroidViewModel(application) {

    val currentLang = MutableLiveData<String>()







}
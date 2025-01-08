package mnshat.dev.myproject.users.patient.profile.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val sharedPreferences: SharedPreferencesManager)
    : ViewModel() {


}
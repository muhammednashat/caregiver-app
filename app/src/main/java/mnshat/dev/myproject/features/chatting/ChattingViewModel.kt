package mnshat.dev.myproject.features.chatting

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.LibraryContent
import mnshat.dev.myproject.util.COMMON_CONTENT
import mnshat.dev.myproject.util.RELIGION
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log

class ChattingViewModel(
    private val sharedPreferences: SharedPreferencesManager,
    application: Application

) : BaseViewModel(
    sharedPreferences,
    application
) {




}
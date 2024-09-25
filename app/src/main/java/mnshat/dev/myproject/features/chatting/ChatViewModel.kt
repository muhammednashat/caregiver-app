package mnshat.dev.myproject.features.chatting

import android.app.Application
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager

class ChatViewModel(
    private val sharedPreferences: SharedPreferencesManager,
    application: Application

) : BaseViewModel(
    sharedPreferences,
    application
) {




}
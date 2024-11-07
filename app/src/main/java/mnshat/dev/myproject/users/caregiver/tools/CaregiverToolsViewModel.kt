package mnshat.dev.myproject.users.caregiver.tools

import android.app.Application
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.model.Step
import mnshat.dev.myproject.util.SharedPreferencesManager

class CaregiverToolsViewModel(
    private val sharedPreferences: SharedPreferencesManager,
    private val  application: Application
) : BaseViewModel(
    sharedPreferences,
    application
) {

    private var currentIndex = 0

    private var currentList = emptyList<Step>()


    fun setCurrentIndex(index: Int) {
        currentIndex = index
    }
    fun getCurrentIndex() = currentIndex
    fun  getCurrentList() = currentList
    fun  setCurrentList(list: List<Step>) {
        currentList = list
    }
}

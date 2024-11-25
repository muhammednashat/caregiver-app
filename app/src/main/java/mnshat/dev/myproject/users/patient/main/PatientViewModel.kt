package mnshat.dev.myproject.users.patient.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mnshat.dev.myproject.base.BaseViewModel2
import mnshat.dev.myproject.util.SharedPreferencesManager

open class PatientViewModel(
    private val sharedPreferences: SharedPreferencesManager,
    application: Application
)
    : BaseViewModel2(
    sharedPreferences,
    application
    ) {

    private var _toolsClick = MutableLiveData<Boolean>()
    val toolsClick:LiveData<Boolean> get()= _toolsClick

    private var _continueClick = MutableLiveData<Boolean>()
    val continueClick:LiveData<Boolean> get()= _continueClick

    private var _educationalContentClicked = MutableLiveData<Boolean>()
    val educationalContentClicked:LiveData<Boolean> get()= _educationalContentClicked


    fun onContinueClick() {
        _continueClick.value = true
    }

    fun onToolsClick() {
        _toolsClick.value = true
    }
    fun onEducationalContentClicked() {
        _educationalContentClicked.value = true
    }


    fun restToolsClick() {
        _toolsClick.value = false
    }
    fun restEducationalContentClicked() {
        _educationalContentClicked.value = false
    }
    fun restContinueClick() {
        _continueClick.value = false
    }


    }


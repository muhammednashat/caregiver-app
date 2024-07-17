package mnshat.dev.myproject.users.patient.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mnshat.dev.myproject.base.BaseViewModel

open class PatientViewModel(application: Application)

    : BaseViewModel(application) {

    private var _toolsClick = MutableLiveData<Boolean>()
    val toolsClick:LiveData<Boolean> get()= _toolsClick

   private var _continueClick = MutableLiveData<Boolean>()
    val continueClick:LiveData<Boolean> get()= _continueClick


    fun onContinueClick() {
        _continueClick.value = true
    }

    fun onToolsClick() {
        _toolsClick.value = true
    }


    fun restToolsClick() {
        _toolsClick.value = false
    }
    fun restContinueClick() {
        _continueClick.value = false
    }


    }
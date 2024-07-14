package mnshat.dev.myproject.users.caregiver

import android.app.Application
import androidx.lifecycle.MutableLiveData
import mnshat.dev.myproject.base.BaseViewModel

class CaregiverViewModel(application: Application)
    : BaseViewModel(application) {


    val isEdited = MutableLiveData<Boolean>()

    init {
        isEdited.value = false
    }
}
package mnshat.dev.myproject.users.patient.dailyprogram

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mnshat.dev.myproject.users.patient.main.BaseUserViewModel

class SharedDailyProgramViewModel(
    application: Application
)

    : BaseUserViewModel(application) {


      private  val _syncTaskUser = MutableLiveData<Boolean>()
       val syncTaskUser:LiveData<Boolean>
          get() = _syncTaskUser






}
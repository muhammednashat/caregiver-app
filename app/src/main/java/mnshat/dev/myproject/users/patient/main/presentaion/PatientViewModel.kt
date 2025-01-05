package mnshat.dev.myproject.users.patient.main.presentaion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.DailyProgramManagerUseCase
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log
import javax.inject.Inject

@HiltViewModel
 class PatientViewModel @Inject constructor(
    private val dailyProgramManagerUseCase: DailyProgramManagerUseCase,
    val sharedPreferences: SharedPreferencesManager,
) : ViewModel() {

    private  val _currentTask = MutableLiveData<CurrentDay>()
    val currentTask: LiveData<CurrentDay> = _currentTask

    fun getCurrentTask() {
        viewModelScope.launch {
         _currentTask.value =   dailyProgramManagerUseCase.getCurrentDayLocallyUseCase()
        }
    }





}


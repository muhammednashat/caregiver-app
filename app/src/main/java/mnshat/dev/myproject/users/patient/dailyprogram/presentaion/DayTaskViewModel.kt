package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.StatusDailyProgram
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.Task
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.DailyProgramManagerUseCase
import mnshat.dev.myproject.util.CURRENT_DAY
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log
import javax.inject.Inject

@HiltViewModel
class DayTaskViewModel @Inject constructor(
    private val dailyProgramManagerUseCase: DailyProgramManagerUseCase,
    val sharedPreferences: SharedPreferencesManager,
) : ViewModel() {

    lateinit var status: StatusDailyProgram
    lateinit var listOfTasks: List<Task>
    val _isSyncNeeded: MutableLiveData<Boolean> = MutableLiveData()

    private  val _currentDay = MutableLiveData<CurrentDay?>()
    val currentDay: LiveData<CurrentDay?> = _currentDay

    fun get() {
        CoroutineScope(Dispatchers.IO).launch {
            _currentDay.postValue(dailyProgramManagerUseCase.getCurrentDayLocallyUseCase())
//            status = _currentDay.value?.status!!
        }
    }



    fun updateCurrentTaskLocally() {
        sharedPreferences.storeObject(CURRENT_DAY,  _currentDay.value)
        _isSyncNeeded.value = true
    }
    fun updateCurrentTaskRemotely() {
//        val map = mapOf(DAY_TASK to currentTask.dayTask!!, STATUS to currentTask.status!!)
//        FirebaseService.updateTasksUser(FirebaseService.userId, map) {
//        }
    }

    override fun onCleared() {
    log("DayTaskViewModel onCleared")
        _currentDay.value = null
        super.onCleared()

    }

}
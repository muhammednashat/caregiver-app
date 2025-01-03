package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.model.StatusDailyProgram
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentTask
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.Task
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.DailyProgramManagerUseCase
import mnshat.dev.myproject.util.CURRENT_TASK
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log
import javax.inject.Inject

@HiltViewModel
class DayTaskViewModel @Inject constructor(
    private val dailyProgramManagerUseCase: DailyProgramManagerUseCase,
    val sharedPreferences: SharedPreferencesManager,
) : ViewModel() {

    var currentTask: CurrentTask? = null
    lateinit var status: StatusDailyProgram
    lateinit var listOfTasks: List<Task>
    val _isSyncNeeded: MutableLiveData<Boolean> = MutableLiveData()
    private  val _isLoaded = MutableLiveData<Boolean>()
    val isLoaded: LiveData<Boolean> = _isLoaded

    fun get() {
        log("DayTaskViewModel get")
        viewModelScope.launch {
            log("DayTaskViewModel viewModelScope")

            currentTask = dailyProgramManagerUseCase.getCurrentTaskLocallyUseCase()
            status= currentTask?.status!!
            _isLoaded.value = true
            log("DayTaskViewModel currentTask $currentTask")
        }
    }
    fun updateCurrentTask(day: Int) {
        viewModelScope.launch {
            dailyProgramManagerUseCase.updateCurrentTaskUseCase(day)
        }
    }

    fun resetIsLoaded(){
        _isLoaded.value = false
    }

    fun updateCurrentTaskLocally() {
        sharedPreferences.storeObject(CURRENT_TASK, currentTask)
        _isSyncNeeded.value = true
    }
    fun updateCurrentTaskRemotely() {
//        val map = mapOf(DAY_TASK to currentTask.dayTask!!, STATUS to currentTask.status!!)
//        FirebaseService.updateTasksUser(FirebaseService.userId, map) {
//        }
    }

}
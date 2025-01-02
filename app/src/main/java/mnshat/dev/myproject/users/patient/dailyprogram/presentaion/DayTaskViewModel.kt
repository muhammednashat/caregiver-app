package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.CurrentTask2
import mnshat.dev.myproject.model.StatusDailyProgram
import mnshat.dev.myproject.model.Task
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentTask
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.DailyProgramManagerUseCase
import mnshat.dev.myproject.util.CURRENT_TASK
import mnshat.dev.myproject.util.DAY_TASK
import mnshat.dev.myproject.util.STATUS
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log
import javax.inject.Inject

@HiltViewModel
class DayTaskViewModel @Inject constructor(
    private val dailyProgramManagerUseCase: DailyProgramManagerUseCase,
    private val sharedPreferences: SharedPreferencesManager,
) : ViewModel() {

    fun get() {

    }

     var currentTask: CurrentTask? = null
    lateinit var status: StatusDailyProgram
    lateinit var listOfTasks: List<Task>
    val _isSyncNeeded: MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewModelScope.launch {
            currentTask = dailyProgramManagerUseCase.getCurrentTaskLocallyUseCase()
            status= currentTask?.status!!
            log("DayTaskViewModel currentTask $currentTask")
        }

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
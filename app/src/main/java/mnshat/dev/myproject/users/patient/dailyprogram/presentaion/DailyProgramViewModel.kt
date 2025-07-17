package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.users.patient.dailyprogram.data.DailyProgramRepository
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.StatusDailyProgram
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.Task
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Inject

@HiltViewModel
class DailyProgramViewModel @Inject constructor(
    private val dailyProgramRepository: DailyProgramRepository,
    val sharedPreferences: SharedPreferencesManager,
) : ViewModel() {

    private var currentDayLocal = dailyProgramRepository.getCurrentDayLocally()
    val userProfile = dailyProgramRepository.getUserProfile()

    lateinit var status: StatusDailyProgram
    lateinit var listOfTasks: List<Task>
     var isSyncNeeded = false

    fun initTasksList(phase:String){
        status = currentDayLocal.status!!
        listOfTasks =  when (phase){
            "educational" ->  currentDayLocal.dayTask?.educational as List<Task>
            "spiritual" ->  currentDayLocal.dayTask?.spiritual as List<Task>
             else -> currentDayLocal.dayTask?.behaviorActivation as List<Task>
        }
    }




    fun updateCompletionRate() {
       status.remaining = status.remaining?.minus(1)
        if (userProfile.religion!!) {
            status.completionRate = status.completionRate?.plus(30)
        } else {
           status.completionRate = status.completionRate?.plus(50)
        }

       updateCurrentTaskLocally()

    }

    fun updateCurrentTaskLocally() {
        currentDayLocal.status = status
        dailyProgramRepository.updateCurrentDayLocally(currentDayLocal)
        isSyncNeeded = true
    }

     fun updateCurrentTaskRemotely() {
         viewModelScope.launch {
             dailyProgramRepository.updateCurrentDayRemotely(currentDayLocal)
             isSyncNeeded = false
         }
    }



}
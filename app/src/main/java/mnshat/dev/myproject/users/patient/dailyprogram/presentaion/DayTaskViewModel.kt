package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mnshat.dev.myproject.users.patient.dailyprogram.data.DailyProgramRepository
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.StatusDailyProgram
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.Task
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Inject

@HiltViewModel
class DayTaskViewModel @Inject constructor(
    private val dailyProgramRepository: DailyProgramRepository,
    val sharedPreferences: SharedPreferencesManager,
) : ViewModel() {

    private var currentDayLocal = dailyProgramRepository.getCurrentDayLocally()
    val userProfile = dailyProgramRepository.getUserProfile()

    lateinit var status: StatusDailyProgram
    lateinit var listOfTasks: List<Task>

    fun initTasksList(phase:String){
        status = currentDayLocal.status!!
        listOfTasks =  when (phase){
            "educational" ->  currentDayLocal.dayTask?.educational as List<Task>
            "spiritual" ->  currentDayLocal.dayTask?.spiritual as List<Task>
            else -> currentDayLocal.dayTask?.behaviorActivation as List<Task>
        }
    }


    val _isSyncNeeded: MutableLiveData<Boolean> = MutableLiveData()


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

        _isSyncNeeded.value = true
    }

    fun updateCurrentTaskRemotely() {

    }



}
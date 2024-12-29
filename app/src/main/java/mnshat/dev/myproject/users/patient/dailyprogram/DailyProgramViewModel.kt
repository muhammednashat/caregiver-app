package mnshat.dev.myproject.users.patient.dailyprogram

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import mnshat.dev.myproject.base.BaseViewModel2
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.CurrentTask
import mnshat.dev.myproject.model.StatusDailyProgram
import mnshat.dev.myproject.model.Task
import mnshat.dev.myproject.util.CURRENT_TASK
import mnshat.dev.myproject.util.DAY_TASK
import mnshat.dev.myproject.util.STATUS
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log

class DailyProgramViewModel(
    private val sharedPreferences: SharedPreferencesManager,
    application: Application

) : BaseViewModel2(
    sharedPreferences,
    application
) {
    var currentTask: CurrentTask
   lateinit var status: StatusDailyProgram
   lateinit var listOfTasks: List<Task>
    val _isSyncNeeded: MutableLiveData<Boolean> = MutableLiveData()

    init {
        currentTask = getCurrntTask()
//        currentTask.status?.preChecked = false
//        log("DailyProgramViewModel init ${currentTask.status?.preChecked}")
        status= currentTask.status!!
    }

    //get Data // 1
    private fun getCurrntTask(): CurrentTask {
        val string = sharedPreferences.getString(CURRENT_TASK, null.toString())
        val gson = Gson()
        return gson.fromJson(string, CurrentTask::class.java)
    }

    fun updateCurrentTaskLocally() {
        sharedPreferences.storeObject(CURRENT_TASK, currentTask)
        _isSyncNeeded.value = true
    }

    fun updateCurrentTaskRemotely() {
        val map = mapOf(DAY_TASK to currentTask.dayTask!!, STATUS to currentTask.status!!)
        FirebaseService.updateTasksUser(FirebaseService.userId, map) {
        }
    }

    override fun onCleared() {
        log("DailyProgramViewModel onCleared")
        super.onCleared()

    }

}
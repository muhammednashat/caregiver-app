package mnshat.dev.myproject.users.patient.dailyprogram.data

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mnshat.dev.myproject.users.patient.dailyprogram.data.daos.DayTaskDao
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentTask
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.DayTaskEntity
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.StatusDailyProgram
import mnshat.dev.myproject.util.CURRENT_TASK
import mnshat.dev.myproject.util.DAILY_PROGRAM_STATES
import mnshat.dev.myproject.util.RELIGION
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USER_EMAIL
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.log
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DayTaskRepository @Inject constructor(
    private val dao: DayTaskDao, private val sharedPreferences: SharedPreferencesManager
) {

    suspend fun getCurrentTask(): CurrentTask {
        return if (dao.getAllDayTasks()?.size != 0) {
                getCurrentTaskLocally()
        } else {
            fetchTasks().let {
                dao.insertAll(it)
                updateCurrentTask(1)
            }
        }
    }

     suspend fun updateCurrentTask(day: Int): CurrentTask {
        val dayTask = getDayTaskFromRoom(day)
        val currentTask = filterBasedProfile(dayTask, day.toString())
        storeCurrentTaskLocally(currentTask)
        storeCurrentTaskRemotely(currentTask)
        return getCurrentTaskLocally()
    }

    private suspend fun fetchTasks():
            List<DayTaskEntity> = suspendCoroutine { continuation ->
           val dayTaskList = mutableListOf<DayTaskEntity>()
           val firestore = FirebaseFirestore.getInstance()
           firestore.collection("daily_programs")
               .get()
               .addOnSuccessListener { querySnapshot ->
                   for (document in querySnapshot) {
                       val dayTask = document.toObject(DayTaskEntity::class.java)
                       dayTaskList.add(dayTask)
                   }
                   continuation.resume(dayTaskList)
               }
               .addOnFailureListener { e ->
                   continuation.resume(emptyList())
                   println("Error fetching data: $e")
               }
       }

    private fun filterBasedProfile(dayTask: DayTaskEntity, day: String): CurrentTask {
        val statusDailyProgram = StatusDailyProgram(day = day.toInt())
        val isReligious = sharedPreferences.getBoolean(RELIGION)
        if (!isReligious) {
            dayTask.spiritual = null
            statusDailyProgram.remaining = 2
        }
        val email = sharedPreferences.getString(USER_EMAIL, null.toString())
        return CurrentTask(email, dayTask, statusDailyProgram)
    }

    private suspend fun getDayTaskFromRoom(day: Int): DayTaskEntity {
        return dao.getDayTaskById(day)!!
    }

    private fun getCurrentTaskLocally(): CurrentTask{
        val string = sharedPreferences.getString(CURRENT_TASK, null.toString())
        val gson = Gson()
        return gson.fromJson(string, CurrentTask::class.java)
    }

    private fun storeCurrentTaskLocally(currentTask: CurrentTask) {
        sharedPreferences.storeObject(CURRENT_TASK, currentTask)
    }

    private fun storeCurrentTaskRemotely(currentTask: CurrentTask) {
         val firebaseDatabase = FirebaseDatabase.getInstance()
        val dailyProgramStates = firebaseDatabase.getReference(DAILY_PROGRAM_STATES)
        val userId = sharedPreferences.getString(USER_ID, null.toString())
        dailyProgramStates.child(userId).setValue(currentTask)
    }









































    suspend fun fetchAndStoreDayTasks() {
        val dayTaskList = mutableListOf<DayTaskEntity>()
        withContext(Dispatchers.IO) {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("daily_programs")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot) {
                        val dayTask = document.toObject(DayTaskEntity::class.java)
                        dayTaskList.add(dayTask)
                    }
                    log("DayTaskRepository fetchAndStoreDayTasks $dayTaskList")
                }
                .addOnFailureListener { e ->
                    println("Error fetching data: $e")
                }
        }
    }
    fun getDayTasks() = mutableListOf<List<DayTaskEntity>>()
    suspend fun getDayTask(id: Int): DayTaskEntity {
        if (dao.getAllDayTasks()?.size  == 0){
            fetchTasks().let {
                log("DayTaskRepository getDayTask 11111  ${it.size}")
                dao.insertAll(it)
                log("DayTaskRepository getDayTask  2222 ${dao.getAllDayTasks()!!.size}")
                return dao.getDayTaskById(id)!!
            }
        }else{
            return dao.getDayTaskById(id)!!
            log("DayTaskRepository getDayTask 3333  ${dao.getAllDayTasks()!!.size}")
        }
    }


}

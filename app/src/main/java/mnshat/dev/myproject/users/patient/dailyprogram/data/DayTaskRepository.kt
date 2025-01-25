package mnshat.dev.myproject.users.patient.dailyprogram.data

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import mnshat.dev.myproject.users.patient.dailyprogram.data.daos.DayTaskDao
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.DayTaskEntity
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.StatusDailyProgram
import mnshat.dev.myproject.util.CURRENT_DAY
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

    suspend fun getCurrentDay(): CurrentDay {
        return if (dao.getAllDayTasks()?.size != 0) {
                getCurrentDayLocally()
        } else {
            fetchDays().let {
                dao.insertAll(it)
                getNextDay(1)
            }
        }
    }

     suspend fun getNextDay(day: Int): CurrentDay {
        val dayTask = getDayTaskFromRoom(day)
        val currentDay = filterBasedProfile(dayTask, day.toString())
         log("currentDay: $currentDay")
         updateCurrentDayLocally(currentDay)
         updateCurrentDayRemotely(currentDay)

        return getCurrentDayLocally()
    }

    private suspend fun fetchDays():
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

    private fun filterBasedProfile(dayTask: DayTaskEntity, day: String): CurrentDay {
        val statusDailyProgram = StatusDailyProgram(day = day.toInt())
        val isReligious = sharedPreferences.getBoolean(RELIGION)
        if (!isReligious) {
            dayTask.spiritual = null
            statusDailyProgram.remaining = 2
            dayTask.behaviorActivation =  dayTask.behaviorActivation?.filter { it?.religion == false }
        }
        val email = sharedPreferences.getString(USER_EMAIL, null.toString())
        log("filterBasedProfile: ${statusDailyProgram.postChecked}")
        return CurrentDay(email, dayTask, statusDailyProgram)
    }

    private suspend fun getDayTaskFromRoom(day: Int): DayTaskEntity {
        return dao.getDayTaskById(day)!!
    }

    private fun getCurrentDayLocally(): CurrentDay{
        val string = sharedPreferences.getString(CURRENT_DAY, null.toString())
        val gson = Gson()
        return gson.fromJson(string, CurrentDay::class.java)
    }


    fun updateCurrentDayLocally(currentDay: CurrentDay) {
        sharedPreferences.storeObject(CURRENT_DAY, currentDay)
    }

    fun updateCurrentDayRemotely(currentDay: CurrentDay){
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val dailyProgramStates = firebaseDatabase.getReference(DAILY_PROGRAM_STATES)
        val userId = sharedPreferences.getString(USER_ID, null.toString())
        dailyProgramStates.child(userId).setValue(currentDay)
    }


}

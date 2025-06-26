package mnshat.dev.myproject.users.patient.dailyprogram.data

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import mnshat.dev.myproject.users.patient.dailyprogram.data.daos.DayTaskDao
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.DayTaskEntity
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.StatusDailyProgram
import mnshat.dev.myproject.util.CURRENT_DAY
import mnshat.dev.myproject.util.DAILY_PROGRAM_STATES
import mnshat.dev.myproject.util.RELIGION
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USERS
import mnshat.dev.myproject.util.USER_EMAIL
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.USER_PROFILE
import mnshat.dev.myproject.util.log
import javax.inject.Inject

class DailyProgramRepository @Inject constructor(
    private val dao: DayTaskDao,
    private val firestore: FirebaseFirestore,
    private val sharedPreferences: SharedPreferencesManager
) {

    suspend fun fetchContentDailyProgram(numberOfDay: Int): Boolean {
        return try {
            var tasks = dao.getAllDayTasks()

            log(tasks?.size.toString())

             if (tasks?.size == 0){
//                 log("tasks?.size == 0")
                 val data = fetchContentDailyProgramRemote()
                 storeDailyProgramListLocally(data)
                 tasks = dao.getAllDayTasks()
              }

//            log("tasks => ${tasks.toString()}")
            val dayTask = dao.getDayTaskById(numberOfDay)
//             log("currentDay: $dayTask")

            val currentDay = filterBasedProfile(dayTask!!, numberOfDay)
//             log("currentDay: $currentDay")
             updateCurrentDayLocally(currentDay)
//              log("current =>>> ${getCurrentDayLocally()}")
            updateCurrentDayRemotely(currentDay)
            true
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun fetchContentDailyProgramRemote(): List<DayTaskEntity> {
        log("Retrieving data ...................")
        val dailyProgramList = mutableListOf<DayTaskEntity>()
        val querySnapShot = firestore.collection("daily_programs").get().await()
        for (document in querySnapShot) {
            dailyProgramList.add(document.toObject(DayTaskEntity::class.java))
        }
        log(dailyProgramList.toString())
        log("Retrieving data has completed")
        return dailyProgramList

       }

    private suspend fun storeDailyProgramListLocally(data: List<DayTaskEntity>) {
        log("Storing start ...................")
        dao.insertAll(data)
        log("Storing completed ...................")

    }

    private fun filterBasedProfile(dayTask: DayTaskEntity, day: Int): CurrentDay {
        val statusDailyProgram = StatusDailyProgram(day = day)
        val userProfile = sharedPreferences.getUserProfile(USER_PROFILE)
        val isReligious = userProfile.religion!!
        if (!isReligious) {
            dayTask.spiritual = null
            statusDailyProgram.remaining = 2
            dayTask.behaviorActivation =  dayTask.behaviorActivation?.filter { it?.religion == false }
        }
        return CurrentDay(userProfile.email, dayTask, statusDailyProgram)
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

    suspend fun updateCurrentDayRemotely(currentDay: CurrentDay){
        try {
            val userProfile = sharedPreferences.getUserProfile(USER_PROFILE)
            firestore.collection(USERS)
                .document(userProfile.id!!)
                .collection("DailyProgram")
                .document(userProfile.id!!)
                .set(currentDay).await()
        }catch (e:Exception){

        }



    //        val firebaseDatabase = FirebaseDatabase.getInstance()
//        val dailyProgramStates = firebaseDatabase.getReference(DAILY_PROGRAM_STATES)
//        val userId = sharedPreferences.getString(USER_ID, null.toString())
//        dailyProgramStates.child(userId).setValue(currentDay)
    }
//
//    suspend fun getCurrentDayRemotely(userId: String): CurrentDay {
//        val firebaseDatabase = FirebaseDatabase.getInstance()
//        val dailyProgramStates = firebaseDatabase.getReference(DAILY_PROGRAM_STATES)
////        val userId = sharedPreferences.getString(USER_ID, null.toString())
//        return dailyProgramStates.child(userId).get().await().value as CurrentDay
//      }



}

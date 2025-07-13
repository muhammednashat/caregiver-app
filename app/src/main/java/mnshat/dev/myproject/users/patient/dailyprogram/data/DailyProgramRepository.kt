package mnshat.dev.myproject.users.patient.dailyprogram.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import mnshat.dev.myproject.users.patient.dailyprogram.data.daos.DayTaskDao
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.DayTaskEntity
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.StatusDailyProgram
import mnshat.dev.myproject.util.CURRENT_DAY
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USERS
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
            log("fetchContentDailyProgram() called with: numberOfDay = $numberOfDay")
            var tasks = dao.getAllDayTasks()
             if (tasks?.size == 0){
                 log("fetchContentDailyProgram() empty")
                 val data = fetchContentDailyProgramRemote()
                 storeDailyProgramListLocally(data)
               }

            val dayTask = dao.getDayTaskById(numberOfDay)
            log("dayTask = $dayTask")

            val currentDay = filterBasedProfile(dayTask!!, numberOfDay)
            log("currentDay = $currentDay")
             updateCurrentDayLocally(currentDay)
             updateNumberDayInUseProfile(numberOfDay)
             updateCurrentDayRemotely(currentDay)

            true
        } catch (e: Exception) {
            log(e.message.toString())
            false
        }
    }

    private suspend fun updateNumberDayInUseProfile(numberOfDay: Int) {
        val userProfile = sharedPreferences.getUserProfile()
        val userId = userProfile.id!!
        val data: HashMap<String, Any> = hashMapOf("currentDay" to numberOfDay)
        try {
            firestore.collection(USERS).document(userId).update(data).await()
        } catch (e: Exception) {
          log(e.message.toString())
        }

    }

    private suspend fun fetchContentDailyProgramRemote(): List<DayTaskEntity> {
        log("Retrieving data ...................")
        val dailyProgramList = mutableListOf<DayTaskEntity>()
        val querySnapShot = firestore.collection("daily_programs").get().await()
        for (document in querySnapShot) {
            dailyProgramList.add(document.toObject(DayTaskEntity::class.java))
        }
        return dailyProgramList

       }

    private suspend fun storeDailyProgramListLocally(data: List<DayTaskEntity>) {
        log("Storing start ...................")
        dao.insertAll(data)
        log("Storing completed ...................")

    }

    private fun filterBasedProfile(dayTask: DayTaskEntity, day: Int): CurrentDay {
        val statusDailyProgram = StatusDailyProgram(day = day)
        val userProfile = sharedPreferences.getUserProfile()
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
        log("updateCurrentDayLocally() called with: currentDay = $currentDay")
        sharedPreferences.storeObject(CURRENT_DAY, currentDay)
    }

    suspend fun updateCurrentDayRemotely(currentDay: CurrentDay){
        try {
            val userProfile = sharedPreferences.getUserProfile()
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

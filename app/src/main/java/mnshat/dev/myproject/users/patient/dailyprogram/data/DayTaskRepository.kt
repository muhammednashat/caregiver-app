package mnshat.dev.myproject.users.patient.dailyprogram.data

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.firebase.FirebaseService.libraryContents
import mnshat.dev.myproject.users.patient.dailyprogram.data.daos.DayTaskDao
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.DayTaskEntity
import mnshat.dev.myproject.util.log
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DayTaskRepository @Inject constructor(private val dao: DayTaskDao)
   {
    suspend fun fetchAndStoreDayTasks() {
        log("DayTaskRepository fetchAndStoreDayTasks ")

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
                   log("DayTaskRepository fetchAndStoreDayTasks $dayTaskList")

               }
               .addOnFailureListener { e ->
                   continuation.resume(emptyList())

                   println("Error fetching data: $e")
               }
       }







    fun getDayTasks() = mutableListOf<List<DayTaskEntity>>()

  suspend  fun getDayTask(id: Int): DayTaskEntity{
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

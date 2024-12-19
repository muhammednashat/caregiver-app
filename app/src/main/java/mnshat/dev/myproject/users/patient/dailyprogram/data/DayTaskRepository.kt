package mnshat.dev.myproject.users.patient.dailyprogram.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mnshat.dev.myproject.users.patient.dailyprogram.data.daos.DayTaskDao
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.DayTaskEntity
import javax.inject.Inject

class DayTaskRepository
    () {

    suspend fun fetchAndStoreDayTasks() {
//        val dayTaskList = mutableListOf<DayTaskEntity>()
//
//        withContext(Dispatchers.IO) {
//            firestore.collection("daily_programs")
//                .get()
//                .addOnSuccessListener { querySnapshot ->
//                }
//                .addOnFailureListener { e ->
//                    println("Error fetching data: $e")
//                }
//        }
    }

    fun getDayTasks() = mutableListOf<List<DayTaskEntity>>()

}

package mnshat.dev.myproject.users.patient.calender.domain.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = DayEntity::class,
            parentColumns = ["day"],
            childColumns = ["day"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val taskId: Int = 0,
    val day: String, // 11 / 11
    val nameTask: String, // Read
    val description: String = "",
    val isCompleted: Boolean = false
)
//data class TaskEntity(
//    @PrimaryKey(autoGenerate = true) val taskId: Int = 0,
//    val day: String, 12 /11
//    val nameTask: String, // Read hs
//    val description: String = "",
//    val isCompleted: Boolean = false
//)


//@Dao
//interface TaskDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertTask(task: TaskEntity)
//
//    @Query("SELECT * FROM tasks WHERE dayDate = :dayDate")
//    suspend fun getTasksForDay(dayDate: String): List<TaskEntity>
//
//    @Update
//    suspend fun updateTask(task: TaskEntity)
//
//    @Delete
//    suspend fun deleteTask(task: TaskEntity)
//}


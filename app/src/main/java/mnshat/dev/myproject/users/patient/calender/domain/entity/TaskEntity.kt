package mnshat.dev.myproject.users.patient.calender.domain.entity

import androidx.room.ForeignKey
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = DayEntity::class,
            parentColumns = ["date"],
            childColumns = ["dayDate"],
            onDelete = ForeignKey.CASCADE // If a day is deleted, its tasks are also removed
        )
    ]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val taskId: Int = 0,
    val dayDate: String, // Foreign key to DayEntity
    val taskName: String,
    val isCompleted: Boolean = false
)


@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE dayDate = :dayDate")
    suspend fun getTasksForDay(dayDate: String): List<TaskEntity>

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)
}


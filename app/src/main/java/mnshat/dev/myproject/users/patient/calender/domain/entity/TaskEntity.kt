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
    val image:Int,
    val nameTask: String, // Read
    val isCompleted: Boolean = false,
    val description: String = "",

)


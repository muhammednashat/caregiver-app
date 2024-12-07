package mnshat.dev.myproject.users.patient.calender.domain.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

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
@Parcelize
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val taskId: Int = 0,
    val day: String, // 11 / 11
    val image:Int,
    val nameTask: String, // Read
    var isCompleted: Boolean = false,
    val description: String = "",
    ) : Parcelable



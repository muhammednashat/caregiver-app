package mnshat.dev.myproject.users.patient.calender.domain.entity

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "days")
data class DayEntity(
    @PrimaryKey val date: String, // Use a unique string like "YYYY-MM-DD" for the date
    val isCurrentDay: Boolean = false // Flag to check if it's today's plan
)


@Dao
interface DayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(day: DayEntity)

    @Query("SELECT * FROM days")
    suspend fun getAllDays(): List<DayEntity>

    @Query("SELECT * FROM days WHERE date = :date")
    suspend fun getDay(date: String): DayEntity?
}

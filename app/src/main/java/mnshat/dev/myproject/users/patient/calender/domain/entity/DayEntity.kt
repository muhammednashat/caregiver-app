package mnshat.dev.myproject.users.patient.calender.domain.entity

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.prolificinteractive.materialcalendarview.CalendarDay


@Entity(tableName = "days")
data class DayEntity(
    @PrimaryKey val day: String
)












//@Dao
//interface DayDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertDay(day: DayEntity)
//
//    @Query("SELECT * FROM days")
//    suspend fun getAllDays(): List<DayEntity>
//
//    @Query("SELECT * FROM days WHERE day = :date")
//    suspend fun getDay(date: String): DayEntity?
//}

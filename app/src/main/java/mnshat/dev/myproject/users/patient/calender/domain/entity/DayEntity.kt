package mnshat.dev.myproject.users.patient.calender.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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

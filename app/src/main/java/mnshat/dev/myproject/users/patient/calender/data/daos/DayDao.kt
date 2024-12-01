package mnshat.dev.myproject.users.patient.calender.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mnshat.dev.myproject.users.patient.calender.domain.entity.DayEntity

@Dao
interface DayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDay(dayEntity: DayEntity):Long

    @Query("SELECT * FROM days WHERE day = :date")
    suspend fun getDay(date: String): DayEntity?
}
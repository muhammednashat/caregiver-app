package mnshat.dev.myproject.users.patient.dailyprogram.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.DayTaskEntity

@Dao
interface DayTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dayTasks: List<DayTaskEntity>)


    @Query("SELECT * FROM DayTaskEntity WHERE id = :id")
    suspend fun getDayTaskById(id: Int): DayTaskEntity?


    @Query("SELECT * FROM DayTaskEntity")
    suspend fun getAllDayTasks(): List<DayTaskEntity>?
}

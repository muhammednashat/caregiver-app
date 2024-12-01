package mnshat.dev.myproject.users.patient.calender.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTasks(tasks: List<TaskEntity>): List<Long>
}
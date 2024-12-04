package mnshat.dev.myproject.users.patient.calender.data.repo

import mnshat.dev.myproject.users.patient.calender.data.daos.TaskDao
import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao)  {

    suspend fun addTasks(tasks: List<TaskEntity>)=
         taskDao.addTasks(tasks)

    suspend fun addTask(task: TaskEntity): Long {
        return  0L
    }

    suspend fun  getTasks(day:String) = taskDao.getTasks(day)

    suspend fun updateTask(task: TaskEntity) {
    }

    suspend fun deleteTask(taskId: Int) {
    }


}

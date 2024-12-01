package mnshat.dev.myproject.users.patient.calender.data.repo

import mnshat.dev.myproject.users.patient.calender.data.daos.TaskDao
import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity
import mnshat.dev.myproject.users.patient.calender.domain.repo.TaskRepository

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun addTasks(tasks: List<TaskEntity>): List<Long> {
        return taskDao.addTasks(tasks)
    }

    override suspend fun addTask(task: TaskEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: TaskEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: Int) {
        TODO("Not yet implemented")
    }


}

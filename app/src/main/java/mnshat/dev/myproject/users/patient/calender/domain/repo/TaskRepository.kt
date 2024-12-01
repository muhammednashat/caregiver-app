package mnshat.dev.myproject.users.patient.calender.domain.repo

import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity


interface TaskRepository {

    suspend fun addTasks(tasks: List<TaskEntity>): List<Long>
    suspend fun addTask(task: TaskEntity): Long
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTask(taskId: Int)

}

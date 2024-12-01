package mnshat.dev.myproject.users.patient.calender.domain.useCase

import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity
import mnshat.dev.myproject.users.patient.calender.domain.repo.TaskRepository


class AddTasksUseCase(private val taskRepository: TaskRepository){
    suspend operator fun invoke(tasks: List<TaskEntity>):Result<List<Long>>{
       return try {
        val ids =  taskRepository.addTasks(tasks)
         Result.success(ids)
        }catch (e:Exception){
         Result.failure(e)
        }
    }
}

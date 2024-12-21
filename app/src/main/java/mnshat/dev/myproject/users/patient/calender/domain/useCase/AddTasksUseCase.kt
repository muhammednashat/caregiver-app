package mnshat.dev.myproject.users.patient.calender.domain.useCase

import mnshat.dev.myproject.users.patient.calender.data.repo.TaskRepository
import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity
import javax.inject.Inject


class AddTasksUseCase @Inject constructor(private val taskRepository: TaskRepository){
    suspend operator fun invoke(tasks: List<TaskEntity>):Result<List<Long>>{
       return try {
        val ids =  taskRepository.addTasks(tasks)
         Result.success(ids)
        }catch (e:Exception){
         Result.failure(e)
        }
    }
}

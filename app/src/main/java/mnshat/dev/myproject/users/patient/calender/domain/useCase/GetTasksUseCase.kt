package mnshat.dev.myproject.users.patient.calender.domain.useCase

import mnshat.dev.myproject.users.patient.calender.data.repo.TaskRepository
import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {


    suspend operator fun  invoke(day:String):Result<List<TaskEntity>>{

        return try {
            Result.success(taskRepository.getTasks(day))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
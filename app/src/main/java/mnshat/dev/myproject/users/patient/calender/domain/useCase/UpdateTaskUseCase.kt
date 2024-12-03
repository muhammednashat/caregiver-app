package mnshat.dev.myproject.users.patient.calender.domain.useCase

import mnshat.dev.myproject.users.patient.calender.data.repo.TaskRepository
import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity

class UpdateTaskUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: TaskEntity): Result<Unit> {
        return try {
            taskRepository.updateTask(task)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
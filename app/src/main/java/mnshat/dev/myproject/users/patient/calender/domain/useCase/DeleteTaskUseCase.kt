package mnshat.dev.myproject.users.patient.calender.domain.useCase

import mnshat.dev.myproject.users.patient.calender.data.repo.TaskRepository
import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity

class DeleteTaskUseCase (private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskId: Int): Result<Unit> {
        return try {
            taskRepository.deleteTask(taskId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
package mnshat.dev.myproject.users.patient.calender.domain.useCase

import mnshat.dev.myproject.users.patient.calender.domain.entity.DayEntity
import mnshat.dev.myproject.users.patient.calender.domain.repo.DayRepository

class CreateDayUseCase (private val dayRepository: DayRepository) {

    suspend operator fun invoke(dayEntity: DayEntity): Result<Long> {
        return try {
           val id =  dayRepository.addDay(dayEntity)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(Exception("Failed to add day: ${dayEntity.day}", e))
        }
    }
}
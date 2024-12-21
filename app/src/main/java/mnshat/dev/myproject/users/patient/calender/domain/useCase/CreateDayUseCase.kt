package mnshat.dev.myproject.users.patient.calender.domain.useCase

import mnshat.dev.myproject.users.patient.calender.data.repo.DayRepository
import mnshat.dev.myproject.users.patient.calender.domain.entity.DayEntity
import mnshat.dev.myproject.util.log
import javax.inject.Inject

class CreateDayUseCase @Inject constructor ( val dayRepository: DayRepository) {

    suspend operator fun invoke(dayEntity: DayEntity): Result<Long> {
        return try {
           val id =  dayRepository.addDay(dayEntity)
            Result.success(id)
        } catch (e: Exception) {
            log("Failed to add day: ${dayEntity.day}")
            Result.failure(Exception("Failed to add day: ${dayEntity.day}", e))
        }
    }
}
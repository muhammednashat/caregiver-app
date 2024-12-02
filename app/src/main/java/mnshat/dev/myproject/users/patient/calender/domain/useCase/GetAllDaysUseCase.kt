package mnshat.dev.myproject.users.patient.calender.domain.useCase

import mnshat.dev.myproject.users.patient.calender.data.repo.DayRepository
import mnshat.dev.myproject.users.patient.calender.domain.entity.DayEntity
import javax.inject.Inject

class GetAllDaysUseCase @Inject constructor(private val dayRepository: DayRepository) {

    suspend operator fun invoke(): Result<List<DayEntity>> {
        return try {
            val days = dayRepository.getAllDays()
            Result.success(days)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

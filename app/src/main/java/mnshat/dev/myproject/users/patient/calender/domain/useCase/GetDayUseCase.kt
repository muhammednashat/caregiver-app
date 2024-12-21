package mnshat.dev.myproject.users.patient.calender.domain.useCase

import mnshat.dev.myproject.users.patient.calender.data.repo.DayRepository
import mnshat.dev.myproject.users.patient.calender.domain.entity.DayEntity
import javax.inject.Inject

class GetDayUseCase @Inject constructor(private val dayRepository: DayRepository) {

    suspend operator fun invoke(date:String): Result<DayEntity?> {
        return try {
            val day = dayRepository.getDay(date)
            Result.success(day)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
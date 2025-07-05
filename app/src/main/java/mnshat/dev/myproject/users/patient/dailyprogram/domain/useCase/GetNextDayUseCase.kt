package mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase

import mnshat.dev.myproject.users.patient.dailyprogram.data.DailyProgramRepository
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import javax.inject.Inject

class GetNextDayUseCase @Inject constructor(private val repository: DailyProgramRepository) {

//    suspend operator fun invoke(day: Int) = repository.getNextDay(day)

    suspend operator fun invoke(day: Int) = CurrentDay()
}
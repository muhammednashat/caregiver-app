package mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase

import mnshat.dev.myproject.users.patient.dailyprogram.data.DailyProgramRepository
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import javax.inject.Inject

class GetCurrentDayLocallyUseCase @Inject constructor(private val repository: DailyProgramRepository) {

//    suspend operator fun invoke () = repository.getCurrentDay()

    suspend operator fun invoke () = CurrentDay()
}
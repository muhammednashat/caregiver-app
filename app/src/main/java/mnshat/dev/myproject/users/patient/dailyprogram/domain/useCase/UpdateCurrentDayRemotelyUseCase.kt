package mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase

import mnshat.dev.myproject.users.patient.dailyprogram.data.DayTaskRepository
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import javax.inject.Inject

class UpdateCurrentDayRemotelyUseCase @Inject constructor(private val repository: DayTaskRepository) {

    suspend operator fun invoke(currentTask: CurrentDay) = repository.updateCurrentDayRemotely(currentTask)
}
package mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase

import mnshat.dev.myproject.users.patient.dailyprogram.data.DayTaskRepository
import javax.inject.Inject

class GetDayTaskUseCase @Inject constructor(private val repository: DayTaskRepository) {

    suspend operator fun invoke(id: Int) = repository.getDayTask(id)

}
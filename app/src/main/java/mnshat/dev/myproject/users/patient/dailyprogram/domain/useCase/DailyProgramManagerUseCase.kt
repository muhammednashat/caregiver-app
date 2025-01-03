package mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase

import javax.inject.Inject

class DailyProgramManagerUseCase @Inject constructor(
     val getCurrentTaskLocallyUseCase: GetCurrentTaskLocallyUseCase,
     val updateCurrentTaskUseCase: UpdateCurrentTaskUseCase,
)

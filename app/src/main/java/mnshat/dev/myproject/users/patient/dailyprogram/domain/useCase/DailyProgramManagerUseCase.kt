package mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase

import javax.inject.Inject

class DailyProgramManagerUseCase @Inject constructor(
     val getCurrentDayLocallyUseCase: GetCurrentDayLocallyUseCase,
     val getNextDayUseCase: GetNextDayUseCase,
     val updateCurrentDayRemotelyUseCase: UpdateCurrentDayRemotelyUseCase,
     val updateCurrentDayLocallyUseCase: UpdateCurrentDayLocallyUseCase
     )

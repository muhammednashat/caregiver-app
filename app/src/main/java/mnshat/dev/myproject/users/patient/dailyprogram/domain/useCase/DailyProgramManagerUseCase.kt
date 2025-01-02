package mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase

import javax.inject.Inject

class DailyProgramManagerUseCase @Inject constructor(
     val getDailyProgramRemotelyUseCase: GetDailyProgramRemotelyUseCase,
     val getDailyProgramLocallyUseCase: GetDailyProgramLocallyUseCase,
     val getDayTaskUseCase: GetDayTaskUseCase,
     val getCurrentTaskLocallyUseCase: GetCurrentTaskLocallyUseCase,
)

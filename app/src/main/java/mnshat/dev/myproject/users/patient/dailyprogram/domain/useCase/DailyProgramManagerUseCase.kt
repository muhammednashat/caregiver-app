package mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase

import javax.inject.Inject

class DailyProgramManagerUseCase @Inject constructor(
    private val getDailyProgramRemotelyUseCase: GetDailyProgramRemotelyUseCase,
    private val getDailyProgramLocallyUseCase: GetDailyProgramLocallyUseCase
)

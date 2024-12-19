package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.DailyProgramManagerUseCase
import javax.inject.Inject

@HiltViewModel
class DayTaskViewModel @Inject constructor(
    private val dailyProgramManagerUseCase: DailyProgramManagerUseCase,
) : ViewModel() {



}
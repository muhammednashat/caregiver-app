package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.DailyProgramManagerUseCase
import mnshat.dev.myproject.util.log
import javax.inject.Inject

@HiltViewModel
class DayTaskViewModel @Inject constructor(
    private val dailyProgramManagerUseCase: DailyProgramManagerUseCase,
) : ViewModel() {

    fun get() {
        viewModelScope.launch {
            val d =      dailyProgramManagerUseCase.getDayTaskUseCase(2)
        }
    }

}
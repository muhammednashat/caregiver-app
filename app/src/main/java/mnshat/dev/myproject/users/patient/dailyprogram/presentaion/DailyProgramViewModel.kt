package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mnshat.dev.myproject.users.patient.dailyprogram.data.DailyProgramRepository
import javax.inject.Inject

@HiltViewModel
class DailyProgramViewModel @Inject constructor(
    private val dailyProgramRepository: DailyProgramRepository
) : ViewModel()
{



}
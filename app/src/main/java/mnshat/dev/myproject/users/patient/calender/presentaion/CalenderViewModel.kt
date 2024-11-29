package mnshat.dev.myproject.users.patient.calender.presentaion

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mnshat.dev.myproject.users.patient.calender.domain.useCase.GetCalenderActivitiesUseCase
import javax.inject.Inject

@HiltViewModel
class CalenderViewModel @Inject constructor(
    private val getCalenderActivitiesUseCase: GetCalenderActivitiesUseCase
) : ViewModel() {

    fun getCalenderActivities(context: Context) = getCalenderActivitiesUseCase(context)

}
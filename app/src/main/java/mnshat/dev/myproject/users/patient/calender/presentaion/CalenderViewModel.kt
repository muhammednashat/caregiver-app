package mnshat.dev.myproject.users.patient.calender.presentaion

import android.content.Context
import androidx.lifecycle.ViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import mnshat.dev.myproject.users.patient.calender.domain.useCase.GetCalenderActivitiesUseCase
import javax.inject.Inject

@HiltViewModel
class CalenderViewModel @Inject constructor(
    private val getCalenderActivitiesUseCase: GetCalenderActivitiesUseCase
) : ViewModel() {

    private lateinit var pickedDate: CalendarDay

    fun getCalenderActivities(context: Context) = getCalenderActivitiesUseCase(context)

    fun setPickedDate(date: CalendarDay) = run { pickedDate = date }
    fun getPickedDate() = pickedDate

}
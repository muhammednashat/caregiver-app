package mnshat.dev.myproject.users.patient.calender.presentaion

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.users.patient.calender.domain.entity.DayEntity
import mnshat.dev.myproject.users.patient.calender.domain.useCase.CalendarUseCaseManager
import mnshat.dev.myproject.util.log
import javax.inject.Inject

@HiltViewModel
class CalenderViewModel @Inject constructor(
    private val calendarUseCaseManager: CalendarUseCaseManager,

) : ViewModel() {

    private lateinit var pickedDate: CalendarDay

    fun getCalenderActivities(context: Context) =
        calendarUseCaseManager.getCalenderActivitiesUseCase(context)

    fun setPickedDate(date: CalendarDay) = run { pickedDate = date }

    fun getPickedDate() = pickedDate

    fun createDayPlan(day: DayEntity) {
        log("createDayPlan")
        viewModelScope.launch {
         val result =  calendarUseCaseManager.createDayUseCase(day)
         if (result.isSuccess){
           val id =  result.getOrNull()
             log("done "+id.toString())
         }else{
             val exception = result.exceptionOrNull()
             log("Failed to create day: ${exception?.message}")
             log("not done"+result.exceptionOrNull())
         }
    }

}
}
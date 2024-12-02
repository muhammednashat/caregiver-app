package mnshat.dev.myproject.users.patient.calender.presentaion

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
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
        CoroutineScope(Dispatchers.IO).launch {
            try {
                log("try")
                val result = calendarUseCaseManager.createDayUseCase(day)
                if (result.isSuccess) {
                    val id = result.getOrNull()
                    log("Successfully created day with ID: $id")
                } else {
                    val exception = result.exceptionOrNull()
                    log("Failed to create day: ${exception?.message}")
                }
            } catch (e: Exception) {
                log("Unexpected error: ${e.message}")
            }
        }
        logAllDays()
    }



    fun logAllDays() {
        log("logAllDays  ")
        CoroutineScope(Dispatchers.IO).launch {
            log("logAllDays  viewModelScope ")

            try {
                val result = calendarUseCaseManager.getAllDaysUseCase()
                if (result.isSuccess) {
                    val days = result.getOrNull()
                    days?.forEach { day ->
                        log("Day: ${day.day},")
                    }
                } else {
                    log("Failed to fetch days: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                log("Unexpected error: ${e.message}")
            }
        }
    }

    fun getDay(date:String) {
        log("getDay  ")
        CoroutineScope(Dispatchers.IO).launch {
            log("getDay  viewModelScope ")
            try {
                val result = calendarUseCaseManager.getDayUseCase(date)
                if (result.isSuccess) {
                    val day = result.getOrNull()
                    day?.let{ day ->
                        log("Day: ${day.day},")
                    }
                } else {
                    log("Failed to fetch days: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                log("Unexpected error: ${e.message}")
            }
        }
    }
}
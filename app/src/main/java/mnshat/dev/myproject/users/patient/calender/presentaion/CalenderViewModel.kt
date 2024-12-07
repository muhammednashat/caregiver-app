package mnshat.dev.myproject.users.patient.calender.presentaion

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mnshat.dev.myproject.users.patient.calender.domain.entity.CalenderActivity
import mnshat.dev.myproject.users.patient.calender.domain.entity.DayEntity
import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity
import mnshat.dev.myproject.users.patient.calender.domain.useCase.CalendarUseCaseManager
import mnshat.dev.myproject.util.log
import java.util.Date
import javax.inject.Inject

//TODO replace CoroutineScope by viewmodelScope OR Cancel The Jop

@HiltViewModel
class CalenderViewModel @Inject constructor(
    private val calendarUseCaseManager: CalendarUseCaseManager,

) : ViewModel() {

    private lateinit var pickedDate: CalendarDay
    private lateinit var customActivity: CalenderActivity
    val today = CalendarDay.today()

    private val _daysList = MutableLiveData<HashSet<CalendarDay>>()
    val daysList: LiveData<HashSet<CalendarDay>> get() = _daysList
    private val _taskList = MutableLiveData<List<TaskEntity>>()
    val taskList: LiveData<List<TaskEntity>> get() = _taskList


    fun getCalenderActivities(context: Context) =
        calendarUseCaseManager.getCalenderActivitiesUseCase(context)

    fun setPickedDate(date: CalendarDay) = run { pickedDate = date }
    fun setCustomActivity(activity: CalenderActivity) = run { customActivity = activity }
    fun getPickedDate() = pickedDate

    fun createDayPlan(day: DayEntity,activities:List<CalenderActivity>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = calendarUseCaseManager.createDayUseCase(day)
                if (result.isSuccess) {
                    val id = result.getOrNull()
                    log("Successfully created day with ID: $id")
                    addTasks(activities.toTaskEntities(day.day))
                } else {
                    val exception = result.exceptionOrNull()
                    log("Failed to create day: ${exception?.message}")
                }
            } catch (e: Exception) {
                log("Unexpected error: ${e.message}")
            }
        }
    }

    fun getDayEntity(dayCalendarDay: CalendarDay) = DayEntity(day = dayCalendarDay.calendar.time.toString())

    fun getDays() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = calendarUseCaseManager.getAllDaysUseCase()
                if (result.isSuccess) {
                    val days = result.getOrNull()
                    days?.let { postDays(days)}
                } else {
                    log("Failed to fetch days: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                log("Unexpected error: ${e.message}")
            }
        }
    }

    private fun postDays(days: List<DayEntity>?) {
        val list = mutableSetOf<CalendarDay>()
        log("Days are${days?.get(0)?.day}")
        days?.get(0)?.day?.let { getTasks(it) }

        days?.forEach { day ->
            val date = Date(day.day)
            val calendarDay = CalendarDay(date)
            list.add(calendarDay)
        }

        _daysList.postValue(list.toHashSet())
    }

    fun getDay(date:String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = calendarUseCaseManager.getDayUseCase(date)
                if (result.isSuccess) {
                    val day = result.getOrNull()
                    day?.let{ day ->
                        log("Day is  ->: ${day.day},")
                    }
                } else {
                    log("Failed to fetch day: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                log("Unexpected error: ${e.message}")
            }
        }
    }

    private fun List<CalenderActivity>.toTaskEntities(day: String) =
        this.map { activity ->
            TaskEntity(
                day = day,
                image = activity.image,
                nameTask = activity.nameTask,
                isCompleted = false,
                description = activity.description
            )
        }

    private fun addTasks(tasks: List<TaskEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = calendarUseCaseManager.addTasksUseCase(tasks)
                if (result.isSuccess){
                    val ids = result.getOrNull()
                     log("the ids are $ids")
                } else {
                    log("Failed to fetch days: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                log("Unexpected error: ${e.message}")
            }
        }
    }

    fun getTasks(day:String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = calendarUseCaseManager.getTasksUseCase(day)
                if (result.isSuccess){
                    val tasks = result.getOrNull()
                    log("tasks are $tasks")
                    tasks?.let { postTasks(it) }
                } else {
                    log("Failed to fetch tasks: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                log("Unexpected error: ${e.message}")
            }
        }
    }

    private fun postTasks(tasks: List<TaskEntity>) {
        _taskList.postValue(tasks)
    }

    fun updateTask(task: TaskEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = calendarUseCaseManager.updateTaskUseCase(task)
                if (result.isSuccess) {
                    log("done: ${result.exceptionOrNull()?.message}")
                } else {
                    log("Failed to update task: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                log("Unexpected error: ${e.message}")
            }
        }

    }


    fun deleteTask(taskId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = calendarUseCaseManager.deleteTaskUseCase(taskId)
                if (result.isSuccess) {
                    log("done: ${result.exceptionOrNull()?.message}")
                } else {
                    log("Failed to delete task: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                log("Unexpected error: ${e.message}")
            }
        }

    }

}
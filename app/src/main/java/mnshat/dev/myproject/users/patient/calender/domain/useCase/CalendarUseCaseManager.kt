package mnshat.dev.myproject.users.patient.calender.domain.useCase

import javax.inject.Inject

class CalendarUseCaseManager @Inject constructor(
    val createDayUseCase: CreateDayUseCase,
    val getCalenderActivitiesUseCase: GetCalenderActivitiesUseCase,
    val getAllDaysUseCase: GetAllDaysUseCase,
    val getDayUseCase: GetDayUseCase,
    val addTasksUseCase: AddTasksUseCase,
    val getTasksUseCase: GetTasksUseCase
)
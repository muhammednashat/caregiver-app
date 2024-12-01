package mnshat.dev.myproject.users.patient.calender.domain.useCase

class CalendarUseCaseManager(
    val addTasksUseCase: AddTasksUseCase,
    val createDayUseCase: CreateDayUseCase,
)
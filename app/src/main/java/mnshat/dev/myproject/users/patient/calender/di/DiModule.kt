package mnshat.dev.myproject.users.patient.calender.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.dataSource.room.AppDatabase
import mnshat.dev.myproject.users.patient.calender.data.daos.DayDao
import mnshat.dev.myproject.users.patient.calender.data.daos.TaskDao
import mnshat.dev.myproject.users.patient.calender.data.repo.CalenderActivitiesRepo
import mnshat.dev.myproject.users.patient.calender.data.repo.DayRepository
import mnshat.dev.myproject.users.patient.calender.data.repo.TaskRepository
import mnshat.dev.myproject.users.patient.calender.domain.useCase.AddTasksUseCase
import mnshat.dev.myproject.users.patient.calender.domain.useCase.CalendarUseCaseManager
import mnshat.dev.myproject.users.patient.calender.domain.useCase.CreateDayUseCase
import mnshat.dev.myproject.users.patient.calender.domain.useCase.DeleteTaskUseCase
import mnshat.dev.myproject.users.patient.calender.domain.useCase.GetAllDaysUseCase
import mnshat.dev.myproject.users.patient.calender.domain.useCase.GetCalenderActivitiesUseCase
import mnshat.dev.myproject.users.patient.calender.domain.useCase.GetDayUseCase
import mnshat.dev.myproject.users.patient.calender.domain.useCase.GetTasksUseCase
import mnshat.dev.myproject.users.patient.calender.domain.useCase.UpdateTaskUseCase
import mnshat.dev.myproject.users.patient.calender.presentaion.CalenderViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  DiModule {

    //ViewModels
    @Provides
    @Singleton
    fun provideCalenderViewModel(calendarUseCaseManager: CalendarUseCaseManager) =
        CalenderViewModel(calendarUseCaseManager)

    //Repos
    @Provides
    @Singleton
    fun provideCalenderActivitiesRepository(sharedPreferences: SharedPreferencesManager) =
        CalenderActivitiesRepo(sharedPreferences)
    @Provides
    @Singleton
    fun providesDayRepository(dayDao: DayDao) = DayRepository(dayDao)
    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao) = TaskRepository(taskDao)

    //UseCases
    @Provides
    fun provideGetCalenderActivitiesUseCase(calenderActivitiesRepo: CalenderActivitiesRepo) =
        GetCalenderActivitiesUseCase(calenderActivitiesRepo)
    @Provides
    fun provideGetAllDaysUseCase(dayRepository: DayRepository) = GetAllDaysUseCase(dayRepository)
    @Provides
    fun provideGetDayUseCase(dayRepository: DayRepository) = GetDayUseCase(dayRepository)
    @Provides
    fun provideCreateDayUseCase(dayRepository: DayRepository) = CreateDayUseCase(dayRepository)
    @Provides
    fun provideUpdateTaskUseCase(taskRepository: TaskRepository) = UpdateTaskUseCase(taskRepository)
    @Provides
    fun provideAddTasksUseCase(taskRepository: TaskRepository) = AddTasksUseCase(taskRepository)
    @Provides
    fun provideDeleteTaskUseCase(taskRepository: TaskRepository) = DeleteTaskUseCase(taskRepository)
    @Provides
    fun provideGetTasksUseCase(taskRepository: TaskRepository) = GetTasksUseCase(taskRepository)
    @Provides
    fun provideCalendarUseCaseManager
                (createDayUseCase: CreateDayUseCase,
                 getCalenderActivitiesUseCase: GetCalenderActivitiesUseCase,
                 getAllDaysUseCase: GetAllDaysUseCase,
                 getDayUseCase: GetDayUseCase,
                 addTasksUseCase: AddTasksUseCase,
                 getTasksUseCase: GetTasksUseCase,
                 updateTaskUseCase: UpdateTaskUseCase,
                 deleteTaskUseCase: DeleteTaskUseCase,
                  ) =
        CalendarUseCaseManager(
            createDayUseCase,
            getCalenderActivitiesUseCase,
            getAllDaysUseCase,
            getDayUseCase,
            addTasksUseCase,
            getTasksUseCase,
            updateTaskUseCase,
            deleteTaskUseCase,
        )

    //Dao
    @Provides
    @Singleton
    fun provideDayDao(appDatabase: AppDatabase) = appDatabase.dayDao()
    @Provides
    @Singleton
    fun provideTaskDao(appDatabase: AppDatabase) = appDatabase.taskDao()

}
package mnshat.dev.myproject.users.patient.calender.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.dataSource.room.AppDatabase
import mnshat.dev.myproject.users.patient.calender.data.daos.DayDao
import mnshat.dev.myproject.users.patient.calender.data.repo.CalenderActivitiesRepo
import mnshat.dev.myproject.users.patient.calender.data.repo.DayRepository
import mnshat.dev.myproject.users.patient.calender.domain.useCase.CalendarUseCaseManager
import mnshat.dev.myproject.users.patient.calender.domain.useCase.CreateDayUseCase
import mnshat.dev.myproject.users.patient.calender.domain.useCase.GetAllDaysUseCase
import mnshat.dev.myproject.users.patient.calender.domain.useCase.GetCalenderActivitiesUseCase
import mnshat.dev.myproject.users.patient.calender.domain.useCase.GetDayUseCase
import mnshat.dev.myproject.users.patient.calender.presentaion.CalenderViewModel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  DiModule {

    //Repos
    @Provides
    @Singleton
    fun provideCalenderActivitiesRepository() = CalenderActivitiesRepo()

    @Provides
    @Singleton
    fun providesDayRepository(dayDao: DayDao) = DayRepository(dayDao)

    //ViewModels
    @Provides
    @Singleton
    fun provideCalenderViewModel(calendarUseCaseManager: CalendarUseCaseManager) =
        CalenderViewModel(calendarUseCaseManager)


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
    fun provideCalendarUseCaseManager
                (createDayUseCase: CreateDayUseCase,
                 getCalenderActivitiesUseCase: GetCalenderActivitiesUseCase,
                 getAllDaysUseCase: GetAllDaysUseCase,
                 getDayUseCase: GetDayUseCase) =
        CalendarUseCaseManager(createDayUseCase,getCalenderActivitiesUseCase,getAllDaysUseCase,getDayUseCase)

    //Dao
    @Provides
   fun  provideDayDao(appDatabase: AppDatabase) = appDatabase.dayDao()

}
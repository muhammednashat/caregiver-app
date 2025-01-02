package mnshat.dev.myproject.users.patient.dailyprogram.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.dataSource.room.AppDatabase
import mnshat.dev.myproject.users.patient.dailyprogram.data.DayTaskRepository
import mnshat.dev.myproject.users.patient.dailyprogram.data.daos.DayTaskDao
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.DailyProgramManagerUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.GetCurrentTaskLocallyUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.GetDailyProgramLocallyUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.GetDailyProgramRemotelyUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.GetDayTaskUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.presentaion.DayTaskViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Di {

    @Provides
    @Singleton
    fun provideDayTaskViewModel(dailyProgramManagerUseCase: DailyProgramManagerUseCase,sharedPreferences: SharedPreferencesManager)
    = DayTaskViewModel(dailyProgramManagerUseCase,sharedPreferences)

    @Provides
    @Singleton
    fun provideDailyProgramManagerUseCase(
        getDailyProgramRemotelyUseCase: GetDailyProgramRemotelyUseCase,
        getDailyProgramLocallyUseCase: GetDailyProgramLocallyUseCase,
        getDayTaskUseCase: GetDayTaskUseCase,
        getCurrentTaskLocallyUseCase: GetCurrentTaskLocallyUseCase,
    ): DailyProgramManagerUseCase {
        return DailyProgramManagerUseCase(
            getDailyProgramRemotelyUseCase, getDailyProgramLocallyUseCase,getDayTaskUseCase,getCurrentTaskLocallyUseCase
        )
    }

    @Provides
    fun provideGetDailyProgramRemotelyUseCase(repository: DayTaskRepository) = GetDailyProgramRemotelyUseCase(repository)

    @Provides
    fun provideGetDailyProgramLocallyUseCase(repository: DayTaskRepository) = GetDailyProgramLocallyUseCase(repository)
   @Provides
   fun provideGetDayTaskUseCase(repository: DayTaskRepository) = GetDayTaskUseCase(repository)

    @Provides
    fun  provideGetCurrentTaskLocallyUseCase(repository: DayTaskRepository) = GetCurrentTaskLocallyUseCase(repository)

    @Provides
    @Singleton
    fun provideDayTaskRepository(dao: DayTaskDao,sharedPreferences: SharedPreferencesManager)  = DayTaskRepository(dao,sharedPreferences)

    @Provides
    @Singleton
    fun provideDayTaskDao(db: AppDatabase) = db.dayTaskDao()


}
package mnshat.dev.myproject.users.patient.dailyprogram.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.util.AppDatabase
import mnshat.dev.myproject.users.patient.dailyprogram.data.DayTaskRepository
import mnshat.dev.myproject.users.patient.dailyprogram.data.daos.DayTaskDao
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.DailyProgramManagerUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.GetCurrentDayLocallyUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.GetNextDayUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.UpdateCurrentDayLocallyUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.UpdateCurrentDayRemotelyUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.presentaion.DayTaskViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Di {


    @Provides
    @Singleton
    fun provideDayTaskViewModel(  dailyProgramManagerUseCase: DailyProgramManagerUseCase,
                                  sharedPreferences: SharedPreferencesManager)= DayTaskViewModel(dailyProgramManagerUseCase,sharedPreferences)

    @Provides
    @Singleton
    fun provideDailyProgramManagerUseCase(
        getCurrentTaskLocallyUseCase: GetCurrentDayLocallyUseCase,
        updateCurrentTaskUseCase: GetNextDayUseCase,
        updateCurrentTaskRemotelyUseCase: UpdateCurrentDayRemotelyUseCase,
        updateCurrentTaskLocallyUseCase: UpdateCurrentDayLocallyUseCase
    ): DailyProgramManagerUseCase {
        return DailyProgramManagerUseCase(
            getCurrentTaskLocallyUseCase,
            updateCurrentTaskUseCase,
            updateCurrentTaskRemotelyUseCase,
            updateCurrentTaskLocallyUseCase
        )
    }

    @Provides
    fun provideUpdateCurrentTaskUseCase(repository: DayTaskRepository) = GetNextDayUseCase(repository)


    @Provides
    fun  provideGetCurrentTaskLocallyUseCase(repository: DayTaskRepository) = GetCurrentDayLocallyUseCase(repository)

    @Provides
    @Singleton
    fun provideDayTaskRepository(dao: DayTaskDao,sharedPreferences: SharedPreferencesManager)  = DayTaskRepository(dao,sharedPreferences)

    @Provides
    @Singleton
    fun provideDayTaskDao(db: AppDatabase) = db.dayTaskDao()


}
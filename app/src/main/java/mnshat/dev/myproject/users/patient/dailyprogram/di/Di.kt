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
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.UpdateCurrentTaskUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.presentaion.DayTaskViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Di {

    @Provides
    @Singleton
    fun provideDailyProgramManagerUseCase(
        getCurrentTaskLocallyUseCase: GetCurrentTaskLocallyUseCase,
        updateCurrentTaskUseCase: UpdateCurrentTaskUseCase,
    ): DailyProgramManagerUseCase {
        return DailyProgramManagerUseCase(
            getCurrentTaskLocallyUseCase,
            updateCurrentTaskUseCase,
        )
    }

    @Provides
    fun provideUpdateCurrentTaskUseCase(repository: DayTaskRepository) = UpdateCurrentTaskUseCase(repository)


    @Provides
    fun  provideGetCurrentTaskLocallyUseCase(repository: DayTaskRepository) = GetCurrentTaskLocallyUseCase(repository)

    @Provides
    @Singleton
    fun provideDayTaskRepository(dao: DayTaskDao,sharedPreferences: SharedPreferencesManager)  = DayTaskRepository(dao,sharedPreferences)

    @Provides
    @Singleton
    fun provideDayTaskDao(db: AppDatabase) = db.dayTaskDao()


}
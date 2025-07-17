package mnshat.dev.myproject.users.patient.dailyprogram.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.util.AppDatabase
import mnshat.dev.myproject.users.patient.dailyprogram.data.DailyProgramRepository
import mnshat.dev.myproject.users.patient.dailyprogram.data.daos.DayTaskDao
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.DailyProgramManagerUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.GetCurrentDayLocallyUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.GetNextDayUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.UpdateCurrentDayLocallyUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.UpdateCurrentDayRemotelyUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.presentaion.DailyProgramViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Di {


    @Provides
    @Singleton
    fun provideDayTaskViewModel(                                    dailyProgramRepository: DailyProgramRepository,
                                  sharedPreferences: SharedPreferencesManager)= DailyProgramViewModel(dailyProgramRepository,sharedPreferences)

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
    fun provideUpdateCurrentTaskUseCase(repository: DailyProgramRepository) = GetNextDayUseCase(repository)


    @Provides
    fun  provideGetCurrentTaskLocallyUseCase(repository: DailyProgramRepository) = GetCurrentDayLocallyUseCase(repository)

    @Provides
    @Singleton
    fun provideDayTaskRepository(dao: DayTaskDao, firestore: FirebaseFirestore, sharedPreferences: SharedPreferencesManager)  = DailyProgramRepository(dao, firestore, sharedPreferences)

    @Provides
    @Singleton
    fun provideDayTaskDao(db: AppDatabase) = db.dayTaskDao()


}
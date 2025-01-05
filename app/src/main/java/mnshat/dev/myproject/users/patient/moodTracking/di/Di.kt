package mnshat.dev.myproject.users.patient.moodTracking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.DailyProgramManagerUseCase
import mnshat.dev.myproject.users.patient.moodTracking.data.MoodRepository
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetEffectingMoodUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetEmojisStatusUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.StoreDayMoodTrackingLocallyUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.StoreDayMoodTrackingRemotelyUseCase
import mnshat.dev.myproject.users.patient.moodTracking.presentaion.MoodViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Di {


    @Provides
    @Singleton
    fun provideMoodRepository() = MoodRepository()

    @Provides
    @Singleton
    fun  provideGetEmojisStatusUseCase(moodRepository: MoodRepository) = GetEmojisStatusUseCase(moodRepository)

    @Provides
    @Singleton
    fun  provideGetEffectingMoodUseCase(moodRepository: MoodRepository) = GetEffectingMoodUseCase(moodRepository)

    @Provides
    fun provideStoreDayMoodTrackingLocallyUseCase(moodRepository: MoodRepository) = StoreDayMoodTrackingLocallyUseCase(moodRepository)

    @Provides
    fun  provideStoreDayMoodTrackingRemotelyUseCase(moodRepository: MoodRepository) = StoreDayMoodTrackingRemotelyUseCase(moodRepository)

    @Provides
    @Singleton
    fun provideMoodViewModel(
        getEmojisStatusUseCase: GetEmojisStatusUseCase,
        getEffectingMoodUseCase: GetEffectingMoodUseCase,
        dailyProgramManagerUseCase: DailyProgramManagerUseCase,
        storeDayMoodTrackingLocallyUseCase: StoreDayMoodTrackingLocallyUseCase,
        storeDayMoodTrackingRemotelyUseCase: StoreDayMoodTrackingRemotelyUseCase,
        sharedPreferences: SharedPreferencesManager
    )
         = MoodViewModel(getEmojisStatusUseCase,getEffectingMoodUseCase,dailyProgramManagerUseCase,sharedPreferences,storeDayMoodTrackingLocallyUseCase,storeDayMoodTrackingRemotelyUseCase,)




}
package mnshat.dev.myproject.users.patient.moodTracking.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.users.patient.dailyprogram.data.DailyProgramRepository
import mnshat.dev.myproject.users.patient.moodTracking.data.MoodTrackingRepository
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetDayTrackingMoodUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetEffectingMoodUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetEmojisStatusUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.StoreDayMoodTrackingLocallyUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.StoreDayMoodTrackingRemotelyUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Di {


    @Provides
    @Singleton
    fun provideMoodRepository(firestore: FirebaseFirestore , dailyProgramRepository: DailyProgramRepository) = MoodTrackingRepository(firestore, dailyProgramRepository)

    @Provides
    @Singleton
    fun  provideGetEmojisStatusUseCase(moodTrackingRepository: MoodTrackingRepository) = GetEmojisStatusUseCase(moodTrackingRepository)

    @Provides
    @Singleton
    fun  provideGetEffectingMoodUseCase(moodTrackingRepository: MoodTrackingRepository) = GetEffectingMoodUseCase(moodTrackingRepository)

    @Provides
    fun provideStoreDayMoodTrackingLocallyUseCase(moodTrackingRepository: MoodTrackingRepository) = StoreDayMoodTrackingLocallyUseCase(moodTrackingRepository)

    @Provides
    fun  provideStoreDayMoodTrackingRemotelyUseCase(moodTrackingRepository: MoodTrackingRepository) = StoreDayMoodTrackingRemotelyUseCase(moodTrackingRepository)


    @Provides
    fun provideGetDayTrackingMoodUseCase (moodTrackingRepository: MoodTrackingRepository) = GetDayTrackingMoodUseCase(moodTrackingRepository)



}
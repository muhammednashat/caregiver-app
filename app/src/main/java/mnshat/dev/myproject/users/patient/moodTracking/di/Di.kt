package mnshat.dev.myproject.users.patient.moodTracking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.users.patient.moodTracking.data.MoodRepository
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetEffectingMoodUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetEmojisStatusUseCase
import mnshat.dev.myproject.users.patient.moodTracking.presentaion.MoodViewModel
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
    @Singleton
    fun  provideMoodViewModel(getEmojisStatusUseCase: GetEmojisStatusUseCase , getEffectingMoodUseCase: GetEffectingMoodUseCase) = MoodViewModel(getEmojisStatusUseCase,getEffectingMoodUseCase)




}
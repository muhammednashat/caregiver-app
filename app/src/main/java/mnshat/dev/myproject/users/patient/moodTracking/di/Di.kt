package mnshat.dev.myproject.users.patient.moodTracking.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.users.patient.dailyprogram.data.DailyProgramRepository
import mnshat.dev.myproject.users.patient.moodTracking.data.MoodTrackingRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Di {

    @Provides
    @Singleton
    fun provideMoodRepository(firestore: FirebaseFirestore , dailyProgramRepository: DailyProgramRepository) = MoodTrackingRepository(firestore, dailyProgramRepository)

}
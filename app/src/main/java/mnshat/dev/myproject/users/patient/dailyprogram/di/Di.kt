package mnshat.dev.myproject.users.patient.dailyprogram.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.util.AppDatabase
import mnshat.dev.myproject.users.patient.dailyprogram.data.DailyProgramRepository
import mnshat.dev.myproject.users.patient.dailyprogram.data.daos.DayTaskDao
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
    fun provideDayTaskRepository(dao: DayTaskDao, firestore: FirebaseFirestore, sharedPreferences: SharedPreferencesManager)  = DailyProgramRepository(dao, firestore, sharedPreferences)

    @Provides
    @Singleton
    fun provideDayTaskDao(db: AppDatabase) = db.dayTaskDao()


}
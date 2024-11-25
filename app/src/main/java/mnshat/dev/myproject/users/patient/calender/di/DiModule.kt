package mnshat.dev.myproject.users.patient.calender.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.users.patient.calender.data.CalenderActivitiesRepo
import mnshat.dev.myproject.users.patient.calender.domain.GetCalenderActivitiesUseCase
import mnshat.dev.myproject.users.patient.calender.presentaion.CalenderViewModel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides
    @Singleton
    fun provideCalenderActivitiesRepository() = CalenderActivitiesRepo()

    @Provides
    fun provideGetCalenderActivitiesUseCase(calenderActivitiesRepo: CalenderActivitiesRepo): GetCalenderActivitiesUseCase {
        return GetCalenderActivitiesUseCase(calenderActivitiesRepo)
    }

    @Provides
    @Singleton
    fun provideCalenderViewModel(getCalenderActivitiesUseCase: GetCalenderActivitiesUseCase)= CalenderViewModel(getCalenderActivitiesUseCase)








}
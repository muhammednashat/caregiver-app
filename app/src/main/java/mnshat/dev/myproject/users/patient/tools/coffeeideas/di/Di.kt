package mnshat.dev.myproject.users.patient.tools.coffeeideas.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion.CofeViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Di {

    @Provides
    @Singleton
    fun  provideCofeViewModel(sharedPreferencesManager: SharedPreferencesManager) = CofeViewModel(sharedPreferencesManager)
}
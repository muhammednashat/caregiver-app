package mnshat.dev.myproject.users.caregiver.tools.cofe.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.users.caregiver.tools.cofe.data.repo.Repository
import mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion.SupportCafeViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Di {

    @Provides
    @Singleton
    fun  provideRepository() = Repository()

    @Provides
    @Singleton
    fun provideSupportCafeViewModel(sharedPreferences: SharedPreferencesManager, repository: Repository) = SupportCafeViewModel(sharedPreferences,repository)
}
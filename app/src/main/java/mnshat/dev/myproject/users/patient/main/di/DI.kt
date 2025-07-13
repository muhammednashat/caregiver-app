package mnshat.dev.myproject.users.patient.main.di
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.users.patient.main.data.UserDataRepository
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DI {
    @Provides
    @Singleton
    fun provideUserDataRepo(
        sharedPreferences: SharedPreferencesManager
    ) = UserDataRepository(  sharedPreferences)


}
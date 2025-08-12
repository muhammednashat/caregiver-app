package mnshat.dev.myproject.users.patient.tools.di
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.users.patient.tools.breath.data.BreathingRepo
import mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion.CofeViewModel
import mnshat.dev.myproject.users.patient.tools.supplications.data.SupplicationsRepo
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DI {

    @Provides
    @Singleton
    fun providerBreathingRepo(
    ) = BreathingRepo()

    @Provides
    @Singleton
    fun  provideCofeViewModel(sharedPreferencesManager: SharedPreferencesManager) = CofeViewModel(sharedPreferencesManager)
    @Provides
    @Singleton
    fun provideSupplicationsRepo(
        firestore: FirebaseFirestore,
        sharedPreferences: SharedPreferencesManager
    ) = SupplicationsRepo(firestore,  sharedPreferences)


}
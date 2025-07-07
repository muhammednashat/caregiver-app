package mnshat.dev.myproject.users.patient.supporters.di
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.users.patient.supporters.data.SupportersRepo
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DI {




    @Provides
    @Singleton
    fun provideSupportersRepo(
        firestore: FirebaseFirestore,
        sharedPreferences: SharedPreferencesManager
    ) = SupportersRepo(firestore,  sharedPreferences)


}
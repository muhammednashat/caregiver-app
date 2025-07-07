package mnshat.dev.myproject.users.patient.profile.di
import com.google.firebase.firestore.FirebaseFirestore
import mnshat.dev.myproject.users.patient.profile.data.ProfileRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Di {
    @Provides
    @Singleton
    fun profileRepo(firestore: FirebaseFirestore
                    , sharedPreferences: SharedPreferencesManager) = ProfileRepo(firestore,sharedPreferences)



}
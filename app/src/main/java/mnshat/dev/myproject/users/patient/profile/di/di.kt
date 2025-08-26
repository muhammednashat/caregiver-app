package mnshat.dev.myproject.users.patient.profile.di
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.users.patient.profile.data.ProfileRepo
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Di {
    @Provides
    @Singleton
    fun profileRepo(
        firestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage,
        sharedPreferences: SharedPreferencesManager
    ) = ProfileRepo(firestore, firebaseStorage, sharedPreferences)



}
package mnshat.dev.myproject.users.patient.tools.supplications

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.users.patient.tools.supplications.data.SupplicationsRepo
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DI {

    @Provides
    @Singleton
    fun provideSupplicationsRepo(
        firestore: FirebaseFirestore,
        sharedPreferences: SharedPreferencesManager
    ) = SupplicationsRepo(firestore,  sharedPreferences)


}
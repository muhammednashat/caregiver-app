package mnshat.dev.myproject.chatting
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.chatting.data.ChattingRepo
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DI {

    @Provides
    @Singleton
    fun provideChattingRepo(
        firestore: FirebaseFirestore,
        sharedPreferences: SharedPreferencesManager
    ) = ChattingRepo(firestore, sharedPreferences)


}
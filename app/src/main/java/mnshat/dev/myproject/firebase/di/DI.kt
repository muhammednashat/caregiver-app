package mnshat.dev.myproject.firebase.di
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Di {
    @Provides
    @Singleton
    fun provideFirebaseFireStore() = FirebaseFirestore.getInstance()




}
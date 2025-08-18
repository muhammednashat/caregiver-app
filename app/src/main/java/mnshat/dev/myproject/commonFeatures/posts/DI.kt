package mnshat.dev.myproject.commonFeatures.posts
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.commonFeatures.posts.data.PostsRepo
import mnshat.dev.myproject.users.patient.supporters.data.repos.SupportersRepo
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DI {




    @Provides
    @Singleton
    fun providePostsRepo(
        firestore: FirebaseFirestore,
        sharedPreferences: SharedPreferencesManager
    ) = PostsRepo(firestore, sharedPreferences)


}
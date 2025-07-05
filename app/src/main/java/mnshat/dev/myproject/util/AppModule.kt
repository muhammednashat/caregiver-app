package mnshat.dev.myproject.util

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.LibraryDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

 @Provides
 @Singleton
 fun provideSharedPreferencesManager(@ApplicationContext context:Context) = SharedPreferencesManager(context)


 @Provides
 @Singleton
 fun provideNetworkMonitor(@ApplicationContext context: Context) = NetworkMonitor(context)

 @Provides
 @Singleton
 fun provideFirebaseFireStore() = FirebaseFirestore.getInstance()

 @Provides
 @Singleton
 fun provideFirebaseAuth() = FirebaseAuth.getInstance()

 @Provides
 @Singleton
 fun provideFirebaseMessaging() = FirebaseMessaging.getInstance()

 @Provides
 @Singleton
 fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
  return AppDatabase.getDatabase(context)
 }

 @Provides
 fun provideLibraryDao(appDatabase: AppDatabase): LibraryDao {
  return appDatabase.libraryDao()
 }


}
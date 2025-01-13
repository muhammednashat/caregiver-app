package mnshat.dev.myproject.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.util.NetworkMonitor
import mnshat.dev.myproject.util.SharedPreferencesManager
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


}
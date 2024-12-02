package mnshat.dev.myproject.dataSource.room.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.LibraryDao
import mnshat.dev.myproject.dataSource.room.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

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

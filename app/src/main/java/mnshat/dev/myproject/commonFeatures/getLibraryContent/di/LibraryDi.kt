package mnshat.dev.myproject.commonFeatures.getLibraryContent.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.LibraryDao
import mnshat.dev.myproject.dataSource.room.AppDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LibraryDi {
    @Provides
    @Singleton
    fun provideLibraryContentRepo(appDatabase: AppDatabase): LibraryDao {
        return appDatabase.libraryDao()
    }


}
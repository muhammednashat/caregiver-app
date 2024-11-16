package mnshat.dev.myproject.commonFeatures.getLibraryContent.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.LibraryDao
import mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion.LibraryViewModel
import mnshat.dev.myproject.dataSource.room.AppDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LibraryDi {

//    @Provides
//    @Singleton
//    fun proLibraViewModel(libraryDao: LibraryDao): LibraryViewModel {
//        return LibraryViewModel(libraryDao)
//    }
    @Provides
    @Singleton
    fun provideLibraryContentRepo(appDatabase: AppDatabase): LibraryDao {
        return appDatabase.libraryDao()
    }


}
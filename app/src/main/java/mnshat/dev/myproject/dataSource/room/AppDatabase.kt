package mnshat.dev.myproject.dataSource.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.LibraryDao
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent

@Database(entities = [LibraryContent::class], version = 2, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {
    abstract fun libraryDao(): LibraryDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database-name"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

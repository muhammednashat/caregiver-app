package mnshat.dev.myproject.dataSource.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.LibraryDao
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.users.patient.calender.data.daos.DayDao
import mnshat.dev.myproject.users.patient.calender.data.daos.TaskDao
import mnshat.dev.myproject.users.patient.calender.domain.entity.DayEntity
import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity

@Database(
    entities = [LibraryContent::class , DayEntity::class,TaskEntity::class],
    version = 5,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun libraryDao(): LibraryDao
    abstract fun dayDao(): DayDao
    abstract fun taskDao(): TaskDao


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

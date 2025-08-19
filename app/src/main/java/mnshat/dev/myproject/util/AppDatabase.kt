package mnshat.dev.myproject.util


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import  mnshat.dev.myproject.getLibraryContent.domain.entity.LibraryContent
import  mnshat.dev.myproject.getLibraryContent.data.LibraryDao
import mnshat.dev.myproject.users.patient.calender.data.daos.DayDao
import mnshat.dev.myproject.users.patient.calender.data.daos.TaskDao
import mnshat.dev.myproject.users.patient.calender.domain.entity.DayEntity
import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity
import mnshat.dev.myproject.users.patient.dailyprogram.data.daos.DayTaskDao
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.DayTaskEntity
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.TaskListConverter

@Database(
    entities = [LibraryContent::class , DayEntity::class,TaskEntity::class, DayTaskEntity::class],
    version = 8,
    exportSchema = false
)
@TypeConverters(TaskListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun libraryDao(): LibraryDao
    abstract fun dayDao(): DayDao
    abstract fun taskDao(): TaskDao
    abstract fun dayTaskDao(): DayTaskDao


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

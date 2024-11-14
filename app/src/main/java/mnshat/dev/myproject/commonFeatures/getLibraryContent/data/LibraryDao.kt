package mnshat.dev.myproject.commonFeatures.getLibraryContent.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent

@Dao
interface LibraryDao {

    @Insert
      fun insertAll(data:List<LibraryContent>)

    @Query("SELECT * FROM library_content")
    suspend  fun getAll(): List<LibraryContent>


}
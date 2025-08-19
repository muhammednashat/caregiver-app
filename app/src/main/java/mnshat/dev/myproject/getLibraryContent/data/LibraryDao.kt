package mnshat.dev.myproject.getLibraryContent.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import mnshat.dev.myproject.getLibraryContent.domain.entity.LibraryContent

@Dao
interface LibraryDao {

    @Insert
    suspend  fun insertAll(data:List<LibraryContent>)

    @Query("SELECT * FROM library_content")
    suspend  fun getAll(): List<LibraryContent>


}
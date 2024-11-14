package mnshat.dev.myproject.commonFeatures.getLibraryContent.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import mnshat.dev.myproject.firebase.FirebaseService.libraryContents
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent

class LibraryContentRepo(
    private val libraryDao: LibraryDao,
) {


    suspend fun getLibraryContent(): List<LibraryContent> {
        var contents = getContentFromRoom()
         if (contents.isEmpty()) {
             retrieveLibraryContent{
                 if (it != null) {
                     libraryDao.insertAll(it)
                     contents = it
                 }
             }
        }

        return contents
    }

    private suspend fun getContentFromRoom() = libraryDao.getAll()


     fun retrieveLibraryContent(onDataFetched: (List<LibraryContent>?) -> Unit) {

        libraryContents.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val libraryContentList = mutableListOf<LibraryContent>()
                for (contentSnapshot in snapshot.children) {
                    val libraryContent = contentSnapshot.getValue(LibraryContent::class.java)
                    libraryContent?.let {
                        libraryContentList.add(it)
                    }
                }
                onDataFetched(libraryContentList)
            }

            override fun onCancelled(error: DatabaseError) {
                onDataFetched(null)
                println("Failed to retrieve data: ${error.message}")
            }
        })
    }


}
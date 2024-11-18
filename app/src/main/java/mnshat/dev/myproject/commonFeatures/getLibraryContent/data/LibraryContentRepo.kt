package mnshat.dev.myproject.commonFeatures.getLibraryContent.data

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import mnshat.dev.myproject.firebase.FirebaseService.libraryContents
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LibraryContentRepo(
    private val libraryDao: LibraryDao,
) {


    suspend fun getLibraryContent(): List<LibraryContent> {
        var contents = getContentFromRoom()
         if (contents.isEmpty()) {
             Log.e("TAG" , "getLibraryContent: null ")
             retrieveLibraryContentFromFirebase().let {
                libraryDao.insertAll(it)
                 Log.e("TAG" , "insertAll ")
                 contents = it
             }
        }
        Log.e("TAG" , " return contents ")
        return contents
    }

    private suspend fun getContentFromRoom() = libraryDao.getAll()

    private suspend fun retrieveLibraryContentFromFirebase(): List<LibraryContent> = suspendCoroutine { continuation ->
        libraryContents.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val libraryContentList = mutableListOf<LibraryContent>()
                for (contentSnapshot in snapshot.children) {
                    val libraryContent = contentSnapshot.getValue(LibraryContent::class.java)
                    libraryContent?.let {
                        libraryContentList.add(it)
                    }
                }
                continuation.resume(libraryContentList)
            }
            override fun onCancelled(error: DatabaseError) {
                continuation.resume(emptyList())
                Log.e("TAG", "Failed to retrieve data: ${error.message}")
            }
        })
    }



}
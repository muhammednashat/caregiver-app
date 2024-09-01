package mnshat.dev.myproject.users.patient.libraraycontent

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.LibraryContent
import mnshat.dev.myproject.util.SharedPreferencesManager

class LibraryViewModel(
    private val sharedPreferences: SharedPreferencesManager,
    application: Application

) : BaseViewModel(
    sharedPreferences,
    application
) {

    private val _libraryContentCustomized = MutableLiveData<List<LibraryContent>>()
    val libraryContentCustomized: LiveData<List<LibraryContent>> = _libraryContentCustomized

    private val _libraryContentMostCommon = MutableLiveData<List<LibraryContent>>()
    val libraryContentMostCommon: LiveData<List<LibraryContent>> = _libraryContentMostCommon

    fun retrieveLibraryContent() {

        FirebaseService.getLibraryContent {
            getLibraryContentCustomized(it)
            getLibraryContentMostCommon(it)
        }


    }

    private fun getLibraryContentMostCommon(libraryContents: List<LibraryContent>?) {
     _libraryContentMostCommon.value = libraryContents!!
    }

    private fun getLibraryContentCustomized(libraryContents: List<LibraryContent>?) {
//        _libraryContentCustomized.value = libraryContents!!
    }
}
package mnshat.dev.myproject.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.useCase.GetLibraryContentUseCase
import mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion.LibraryViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager


class LibraryViewModelFactory

    (

    private val sharedPreferences: SharedPreferencesManager,
    private val getLibraryContentUseCase: GetLibraryContentUseCase,
    private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LibraryViewModel(sharedPreferences,getLibraryContentUseCase,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
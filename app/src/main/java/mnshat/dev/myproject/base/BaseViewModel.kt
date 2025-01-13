package mnshat.dev.myproject.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.util.NetworkMonitor
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    val sharedPreferencesManager: SharedPreferencesManager,
    private val networkMonitor: NetworkMonitor):ViewModel() {


    val isNetworkAvailable: LiveData<Boolean> = liveData {
        networkMonitor.observeNetworkStatus().collect { isAvailable ->
            emit(isAvailable)
        }
    }

}
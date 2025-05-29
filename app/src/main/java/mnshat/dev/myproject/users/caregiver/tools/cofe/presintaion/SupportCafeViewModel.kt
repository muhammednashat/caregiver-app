package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import mnshat.dev.myproject.users.caregiver.tools.cofe.data.repo.Repository
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Inject

@HiltViewModel

class SupportCafeViewModel @Inject constructor(
    sharedPreferences: SharedPreferencesManager,

    private val repository: Repository): ViewModel()  {


    fun getPhrases(context: Context) = repository.getPhrases(context)

}
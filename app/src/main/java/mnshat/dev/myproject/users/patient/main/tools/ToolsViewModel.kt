package mnshat.dev.myproject.users.patient.main.tools

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log

class ToolsViewModel(private val sharedPreferences: SharedPreferencesManager,
                     application: Application
):BaseViewModel(sharedPreferences,application) {

    private val _suggestedSupplication = MutableLiveData<List<Supplication>>()
    val suggestedSupplication: LiveData<List<Supplication>>
        get() = _suggestedSupplication


    private val _userSupplication = MutableLiveData<List<Supplication>>()
    val userSupplication: LiveData<List<Supplication>>
        get() = _userSupplication

    init {
        _suggestedSupplication.value = supplications
        _userSupplication.value = supplications
    }




}


val supplications = listOf(
    Supplication("Supplication 1", 10),
    Supplication("Supplication 2", 20),
    Supplication("Supplication 3", 30),
    Supplication("Supplication 1", 10),
    Supplication("Supplication 2", 20),
    Supplication("Supplication 3", 30)
)
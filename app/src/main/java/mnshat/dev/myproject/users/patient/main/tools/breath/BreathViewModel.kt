package mnshat.dev.myproject.users.patient.main.tools.breath

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.model.Duration
import mnshat.dev.myproject.util.SharedPreferencesManager

class BreathViewModel(
    private val sharedPreferences: SharedPreferencesManager,
    application: Application
) : BaseViewModel(sharedPreferences, application) {

    private var _selectedPosition:Int=0

    fun getSelectedPosition() = _selectedPosition


    private val  _currentDuration = MutableLiveData<String>()
    val currentDuration: LiveData<String>
        get() = _currentDuration




    fun setCurrentDuration(index:Int){
        _selectedPosition = index
        _currentDuration.value=getListOfDurations()[index].text
    }


    fun getListOfDurations(): List<Duration> {
        return listOf(
            Duration(1,"1.00 دقيقه / دقائق"),
            Duration(2,"2.00 دقيقه / دقائق"),
            Duration(3,"3.00 دقيقه / دقائق"),
            Duration(4,"4.00 دقيقه / دقائق"),
            Duration(5,"5.00 دقيقه / دقائق"),
            Duration(6,"6.00 دقيقه / دقائق"),
            Duration(7,"7.00 دقيقه / دقائق"),
            Duration(8,"8.00 دقيقه / دقائق"),

        )
    }


}
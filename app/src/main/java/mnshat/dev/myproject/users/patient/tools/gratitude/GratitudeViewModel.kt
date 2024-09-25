package mnshat.dev.myproject.users.patient.tools.gratitude

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.GratitudeAdapter
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.Duration
import mnshat.dev.myproject.model.Gratitude
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.getGratitudeQuestionsList

class GratitudeViewModel(
    private val sharedPreferences: SharedPreferencesManager,
    application: Application
) : BaseViewModel(sharedPreferences, application) {

    private var _selectedPosition:Int=0
    private var counter :Int = 0

    fun getSelectedPosition() = _selectedPosition

    fun setSelectedPosition(randomNumber: Int) {
        _selectedPosition = randomNumber
    }
    fun sendGratitude(gratitude: Gratitude,callBack:(Boolean) -> Unit) {
        FirebaseService.retrieveUser(null, FirebaseService.userEmail!!) { user ->
            user?.let{
                val gratitudeList: MutableList<Gratitude> = user.gratitudeList ?: mutableListOf()
                gratitudeList.add(gratitude)
                val map = mapOf<String, Any>("gratitudeList" to gratitudeList)
                FirebaseService.updateItemsProfileUser(FirebaseService.userId, map) {

                    if (it) {
                        callBack(true)
                        Log.e("TAG" , " 3333333333333")
                    } else {
                        callBack(false)
                        Log.e("TAG" , " 444444444444444")
                    }

                }
            }

        }
    }

}
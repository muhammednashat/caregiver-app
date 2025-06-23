package mnshat.dev.myproject.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import mnshat.dev.myproject.R
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.GENDER
import mnshat.dev.myproject.util.NetworkMonitor
import mnshat.dev.myproject.util.SharedPreferencesManager

open class BaseViewModel2(
    private val sharedPreferences: SharedPreferencesManager,
   private val application: Application
) : AndroidViewModel(application) {
    var intGender = MutableLiveData<Int?>()
    var strGender = MutableLiveData<String?>()
    var intAge = MutableLiveData<Int?>()
    var strAge = MutableLiveData<String?>()
    val currentLang = MutableLiveData<String>()
    var strDialect = MutableLiveData<String?>()
    var intDialect = MutableLiveData<Int?>()


    fun setGender(int: Int) {
        intGender.value = int
    }

    fun setAge(int: Int) {
        intAge.value = int
    }

    fun setDialect(int: Int) {
        intDialect.value = int
    }

    fun setStrGender(context: Context, sharedPreferences: SharedPreferencesManager, gender: Int?) {
        gender?.let {
            sharedPreferences.storeInt(GENDER, gender)
            when (gender) {
                1 -> {
                    strGender.value = context.getString(R.string.male)
                }

                2 -> {
                    strGender.value = context.getString(R.string.female)
                }
            }
        }
    }
fun isConnected():Boolean{
    val networkMonitor = NetworkMonitor(application)
    return  networkMonitor.isConnected()
}
    fun setStrAge(context: Context, sharedPreferences: SharedPreferencesManager, age: Int?) {
        age?.let {
            sharedPreferences.storeInt(AGE_GROUP, age)
            when (age) {
                1 -> {
                    strAge.value = context.getString(R.string.young_adulthood1)
                }

                2 -> {
                    strAge.value = context.getString(R.string.middle_age1)
                }

                3 -> {
                    strAge.value = context.getString(R.string.older)
                }
            }
        }
    }


    fun setFavoriteLange(lang: String) {
        currentLang.value = lang
    }






}
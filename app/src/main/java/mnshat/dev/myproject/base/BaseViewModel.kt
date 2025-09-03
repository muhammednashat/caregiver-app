package mnshat.dev.myproject.base

import androidx.lifecycle.ViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val fireAnalytics: FirebaseAnalytics,
    private val sharedPreferences: SharedPreferencesManager
) : ViewModel() {

    fun currentUserProfile() = sharedPreferences.getUserProfile()

    fun updateUserPropertyAnalytics() {
        val userId = currentUserProfile().id
        val name = currentUserProfile().name
        fireAnalytics.setUserProperty("user_name", name)
        fireAnalytics.setUserId(userId)
        log("updating has done")
        log("user id $userId")
        log("user name $name")


    }
}
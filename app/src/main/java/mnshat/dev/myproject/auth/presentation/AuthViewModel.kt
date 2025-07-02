package mnshat.dev.myproject.auth.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.R
import mnshat.dev.myproject.auth.data.entity.UserProfile
import mnshat.dev.myproject.auth.data.repo.AuthRepo
import mnshat.dev.myproject.users.patient.dailyprogram.data.DailyProgramRepository
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.IS_LOGGED
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.TYPE_OF_USER
import mnshat.dev.myproject.util.USER
import mnshat.dev.myproject.util.log
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class AuthViewModel @Inject constructor(
    val sharedPreferences: SharedPreferencesManager,
    private val dailyProgramRepo: DailyProgramRepository,
    private val authRepo: AuthRepo,
) : ViewModel() {

    var context:Context? = null
    var partner: UserProfile? = null
    private var _authStatus = MutableLiveData<String?>()
    val authStatus: LiveData<String?> = _authStatus
    var intGender = MutableLiveData<Int?>()
    var strGender = MutableLiveData<String?>()
    var intAge = MutableLiveData<Int?>()
    var strAge = MutableLiveData<String?>()
    val currentLang = MutableLiveData<String>()
    var name = MutableLiveData<String?>()
    var invitationCode = MutableLiveData<String>()
    var email = MutableLiveData<String?>()
    var password = MutableLiveData<String?>()
    val typeOfUser = MutableLiveData<String?>()
    var errorMessage: String? = ""





    fun userProfile(): UserProfile {
//        return UserProfile(
//            name = "name.value",
//            email = "emailfdffalue@gmail.com",
//            password = "password.value",
//            gender = 2,
//            ageGroup = 2,
////            gender = intGender.value,
////            ageGroup = intAge.value,
//            typeOfUser = USER,


        return UserProfile(
            name = name.value,
            email = email.value,
            password = password.value,
//            gender = 2,
//            ageGroup = 2,
            gender = intGender.value,
            ageGroup = intAge.value,
            typeOfUser = typeOfUser.value ?: "",
        )
    }

    fun signUp( context: Context) {
        this.context = context

        try {

            viewModelScope.launch {

                if (userProfile().typeOfUser == CAREGIVER) {

                    isValidInvitation(invitationCode.value!!)

                } else {

                    userRegistration(userProfile())
                }

            }
        }
        catch (e: Exception) {

            log("${e.message}")

        }

    }

    private suspend fun isValidInvitation(invitationCode: String) {
        val partner = authRepo.isValidInvitation(invitationCode)
        if (partner != null) {
            this.partner = partner
            caregiverRegistration(userProfile())
        } else {
            _authStatus.value = context?.getString(R.string.user_not_found)
        }
    }

    private suspend fun caregiverRegistration(userProfile: UserProfile) {
        val result = authRepo.signUp(userProfile)
        if (result.isNotEmpty()) {
            _authStatus.value = result
        } else {
            addPartnerToCaregiver(userProfile.id ?: "")
        }
    }

    private suspend fun addPartnerToCaregiver(userId: String) {
        val result = authRepo.linkPartnerToUser(userId, partner?.id ?: "")
        if (result.isNotEmpty()) {
            _authStatus.value = result
        } else {
            addSupporterToUser(userId)
        }
    }

    private suspend fun addSupporterToUser(partnerId: String) {
        val result = authRepo.linkPartnerToUser(partner?.id ?: "", partnerId)
        if (result.isNotEmpty()) {
            _authStatus.value = result
            log(result)
        } else {
            updateUserData()
        }
    }

    private suspend fun updateUserData(){
        val updatedNumber = partner?.supportersNumber!!.plus(1)
        val updatedData: HashMap<String, Any> = hashMapOf(
            "supportersNumber" to updatedNumber,
            "hasPartner" to true,
            "invitationCode" to "%%%%%%%$$$$%%%%%%%%%%",
            "invitationUsed" to true
        )
        val result = authRepo.updateUserData(partner?.id ?: "", updatedData)
        if (result.isNotEmpty()) {
            _authStatus.value = result
            log(result)
        } else {
            _authStatus.value = ""
            log("done")
        }
    }

    private suspend fun userRegistration(userProfile: UserProfile) {

        val result = authRepo.signUp(userProfile)



        if (result.isNotEmpty()) {
            _authStatus.value = result
            log(result)
        } else {

            fetchContentDailyProgramRemote(1)
        }

    }

    private fun fetchContentDailyProgramRemote(numberOfDay: Int) {

        viewModelScope.launch {
            val result = dailyProgramRepo.fetchContentDailyProgram(numberOfDay)
            if (result) {
                _authStatus.value = ""
            } else {
                _authStatus.value = "error"
            }
        }
    }

    fun validToLogin(context: Context): Boolean {
        log("validToLogin() called with: context = $context")
        if (!isEmailValid(context)) {
            return false
        } else if (!isPasswordValid(context)) {
            return false
        }
        return true
    }

    fun setAge(int: Int) {
        intAge.value = int
    }

    fun setStrAge(context: Context) {
        intAge.value?.let {
            when (intAge.value) {
                1 -> strAge.value = context.getString(R.string.young_adulthood1)
                2 -> strAge.value = context.getString(R.string.middle_age1)
                3 -> strAge.value = context.getString(R.string.older)
            }
        }
    }



    fun setFavoriteLange(lang: String) {
        currentLang.value = lang
    }

    fun setGender(int: Int) {
        intGender.value = int
    }
    fun setStrGender(context: Context ) {
        val gender = intGender.value
        gender?.let {
            when (gender) {
                1 -> strGender.value = context.getString(R.string.male)
                2 -> strGender.value = context.getString(R.string.female)
            }
        }
    }

    fun validToRegisterUser(context: Context): Boolean {
        if (!isValidUserType(context)) return false
        if (!isValidName(context)) return false
        if (!isEmailValid(context)) return false
        if (!isPasswordValid(context)) return false
        if (!isValidAgeGroup(context)) return false
        if (!isValidGender(context)) return false
        if (typeOfUser.value == CAREGIVER) return isValidInvitationCode(context)
        return true
    }

    private fun isEmailValid(context: Context): Boolean {
        log("isEmailValid() called with: context = $context")
        return if (email.value.isNullOrEmpty()) {
            errorMessage = context?.getString(R.string.enter_email)
            false
        } else {
            email.value = email.value!!.trim()
            log(email.value!!)
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (!email.value!!.matches(emailPattern.toRegex())) {
                errorMessage = context?.getString(R.string.email_error)
                false
            } else true
        }
    }

    private fun isPasswordValid(context: Context): Boolean {
        log("isPasswordValid() called with: context = $context")
        return if (password.value.isNullOrEmpty()) {
            errorMessage = context?.getString(R.string.enter_password)
            false
        } else {
            if (password.value!!.trim().length < 6) {
                errorMessage = context?.getString(R.string.pass_error)
                false
            } else true
        }
    }

    private fun isValidUserType(context: Context): Boolean {
        return if (typeOfUser.value.isNullOrEmpty()) {
            errorMessage = context?.getString(R.string.select_user_type)
            false
        } else true
    }

    private fun isValidName(context: Context): Boolean {
        log("isValidName() called with: name = ${name.value}")
        return if (name.value.isNullOrEmpty()) {
            errorMessage = context?.getString(R.string.enter_username)
            false
        } else true
    }

    private fun isValidAgeGroup(context: Context): Boolean {
        return if (typeOfUser.value == CAREGIVER) {
            true
        } else {
            if (strAge.value == context?.getString(R.string.age_group)) {
                errorMessage = context?.getString(R.string.select_age_category)
                false
            } else true
        }
    }

    private fun isValidGender(context: Context): Boolean {
        return if (typeOfUser.value == CAREGIVER) {
            true
        } else {
            if (strGender.value == context?.getString(R.string.gender)) {
                errorMessage = context?.getString(R.string.select_gender)
                false
            } else {
                true
            }
        }
    }

    private fun isValidInvitationCode(context: Context): Boolean {
        return if (this.invitationCode.value.isNullOrEmpty()) {
            errorMessage = context?.getString(R.string.enter_invitation)
            false
        } else true
    }

    fun clearData() {
        name.value = null
        email.value = null
        password.value = null
        typeOfUser.value = null
        intAge.value = null
        intGender.value = null
    }
    fun currentUserProfile() = authRepo.currentUserProfile()



    fun updateAuthStatusLocale() {
        val userType = currentUserProfile().typeOfUser
        sharedPreferences.storeBoolean(IS_LOGGED,true)
        sharedPreferences.storeString(TYPE_OF_USER, userType)
    }

    fun login() {
        viewModelScope.launch {
            val result: String =
                authRepo.signInWithEmailAndPassword(email.value!!, password.value!!)
            if (result.isEmpty()) {
          retrieveUserRemote()
      }else{
          _authStatus.value = result
      }
        }
    }

    private suspend fun retrieveUserRemote() {

        val (userProfile, message) = authRepo.retrieveUserRemoteByEmail(email.value!!)
        if (message.isNotEmpty()) {
            log("retrieveUserRemote() error ")
            _authStatus.value = "Error, please try again."
        } else {
            log("retrieveUserRemote() success ")
            storeUserLocally(userProfile!!)
        }

    }

    private fun storeUserLocally(userProfile: UserProfile) {
        try {
            val result = authRepo.storeUserDataLocally(userProfile)
            if (result) {
                log("storeUserLocally() success ")
                checkUsertype(userProfile)
            } else {
                log("storeUserLocally() error ")
                _authStatus.value = "Error, please try again."
            }
        } catch (e: Exception) {
            log("storeUserLocally() Exception ${e.message} ")
            _authStatus.value = "Error, please try again."
        }

    }

    private fun checkUsertype(userProfile: UserProfile) {
        log(userProfile.typeOfUser.toString())

        when (userProfile.typeOfUser) {
            CAREGIVER -> {
                log("CAREGIVER")
                _authStatus.value = ""
            }

            USER -> {
                log("USER")
                fetchContentDailyProgramRemote(userProfile.currentDay!!)
            }
        }

    }


    fun  resetAuthStatus() {
        _authStatus.value = null
    }


}

package mnshat.dev.myproject.auth.presentation

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.R
import mnshat.dev.myproject.auth.data.entity.UserProfile
import mnshat.dev.myproject.auth.data.repo.AuthRepo
import mnshat.dev.myproject.model.Partner
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.GENDER
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    val sharedPreferences: SharedPreferencesManager,
    val authRepo: AuthRepo,
) : ViewModel() {

    var intGender = MutableLiveData<Int?>()
    var strGender = MutableLiveData<String?>()
    var intAge = MutableLiveData<Int?>()
    var strAge = MutableLiveData<String?>()
    val currentLang = MutableLiveData<String>()
    var name = MutableLiveData<String?>()
    var token = MutableLiveData<String>()
    var invitationCode = MutableLiveData<String>()
    var email = MutableLiveData<String?>()
    var password = MutableLiveData<String?>()
    val typeOfUser = MutableLiveData<String?>()
    var errorMessage: String? = ""
    var id: String? = null
    var imageUser: String? = null ;



    fun testStoringRe(){
        val fakeUser = UserProfile(
            id = "user001",
            name = "Alice Smith",
            email = "alice@example.com",
            password = "secret123",
            imageUser = "https://example.com/profile.jpg",
            gender = 0,
            ageGroup = 2,
            religion = true,
            token = "fake_token_abc123",
            invitationCode = "INVITE2025",
            typeOfUser = "standard",
            isInvitationUsed = false,
            numberSupporters = 10,
            hasPartner = true,
            invitationBase = "BASE789",
            status = 1
        )


        viewModelScope.launch {
            log("testStoringRe()")
            authRepo.signUp(fakeUser)
        }

    }
    fun clearData() {
        name.value = null
        email.value = null
        password.value = null
        typeOfUser.value = null
        intAge.value = null
        intGender.value = null
    }


    fun validToLogin(context: Context): Boolean {
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


    fun setFavoriteLange(lang: String) {
        currentLang.value = lang
    }
    fun setGender(int: Int) {
        intGender.value = int
    }
    fun validToRegisterUser(context: Context): Boolean {

        if (!isValidUserType(context)) {
            return false
        } else if (!isValidName(context)) {
            return false
        } else if (!isEmailValid(context)) {
            return false
        } else if (!isPasswordValid(context)) {
            return false
        } else if (!isValidAgeGroup(context)) {
            return false
        } else if (!isValidGender(context)) {
            return false
        } else if (typeOfUser.value == CAREGIVER) {
            return isValidInvitationCode(context)
        }
        return true
    }

    private fun isEmailValid(context: Context): Boolean {
        return if (email.value.isNullOrEmpty()) {
            errorMessage = context.getString(R.string.enter_email)
            false
        } else {
            email.value = email.value!!.trim()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (!email.value!!.matches(emailPattern.toRegex())) {
                errorMessage = context.getString(R.string.email_error)
                false
            } else
                true
        }
    }

    private fun isPasswordValid(context: Context): Boolean {
        return if (password.value.isNullOrEmpty()) {
            errorMessage = context.getString(R.string.enter_password)
            false
        } else {
            if (password.value!!.trim().length < 6) {
                errorMessage = context.getString(R.string.pass_error)
                false
            } else true
        }
    }

    private fun isValidUserType(context: Context): Boolean {
        return if (typeOfUser.value.isNullOrEmpty()) {
            errorMessage = context.getString(R.string.select_user_type)
            false
        } else true
    }

    private fun isValidName(context: Context): Boolean {
        return if (name.value.isNullOrEmpty()) {
            errorMessage = context.getString(R.string.enter_username)
            false
        } else true
    }

    private fun isValidAgeGroup(context: Context): Boolean {
        return if (typeOfUser.value == CAREGIVER) {
            true
        } else {
            if (strAge.value == context.getString(R.string.age_group)) {
                errorMessage = context.getString(R.string.select_age_category)
                false
            } else true

        }
    }

    private fun isValidGender(context: Context): Boolean {
        return if (typeOfUser.value == CAREGIVER) {
            true
        } else {
            if (strGender.value == context.getString(R.string.gender)) {
                errorMessage = context.getString(R.string.select_gender)
                false
            } else true
        }
    }

    private fun isValidInvitationCode(context: Context): Boolean {
        return if (this.invitationCode.value.isNullOrEmpty()) {
            errorMessage = context.getString(R.string.enter_invitation)
            false
        } else true
    }



    fun getRegistrationDataForUser(): UserProfile {
        initImageUser()
        return UserProfile(
            id = id,
            name = name.value,
            email = email.value,
            imageUser = imageUser,
            gender = intGender.value,
            ageGroup = intAge.value,
            token = token.value,
            invitationCode = invitationCode.value,
            typeOfUser = typeOfUser.value ?: "",
            numberSupporters = 0,
            hasPartner = false,
            status = 1,
            religion = true,
        )
    }

    private fun initImageUser() {
         imageUser =       if (intGender.value == 1) "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2Fman.png?alt=media&token=442a95f6-c82c-4725-9432-5fef0b516b06" else "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2Fusers%20(2).png?alt=media&token=889b15ae-fd46-4255-a757-de712e1b5113"
    }

}
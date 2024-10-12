package mnshat.dev.myproject.auth

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.model.Partner
import mnshat.dev.myproject.model.Permissions
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.EMAIL_PARTNER
import mnshat.dev.myproject.util.GENDER
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.ID_PARTNER
import mnshat.dev.myproject.util.IMAGE_PARTNER
import mnshat.dev.myproject.util.IS_LOGGED
import mnshat.dev.myproject.util.NAME_PARTNER
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.TYPE_OF_USER
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.USER_IMAGE
import mnshat.dev.myproject.util.USER_NAME
import mnshat.dev.myproject.util.log

class AuthViewModel(
    private val sharedPreferences: SharedPreferencesManager,
    application: Application

) : BaseViewModel(
    sharedPreferences,
    application
) {

    var partner: Partner? = null

    var name = MutableLiveData<String?>()
    var token = MutableLiveData<String>()
    var invitationCode = MutableLiveData<String>()
    var email = MutableLiveData<String?>()


    var password = MutableLiveData<String?>()
    val typeOfUser = MutableLiveData<String?>()
    var errorMessage: String? = ""
    var id: String? = null
    var imageUser: String? = "123"

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


    fun storeDataLocally() {
        sharedPreferences.storeString(USER_ID, id)
        sharedPreferences.storeString(TYPE_OF_USER, typeOfUser.value)
        sharedPreferences.storeString(USER_NAME, name.value)
        sharedPreferences.storeString(USER_IMAGE, imageUser)
        sharedPreferences.storeInt(AGE_GROUP, intAge.value)
        sharedPreferences.storeInt(GENDER, intGender.value)
        sharedPreferences.storeBoolean(HAS_PARTNER, false)
        sharedPreferences.storeBoolean(IS_LOGGED, true)
        sharedPreferences.storeString(NAME_PARTNER, partner?.namePartner)
        sharedPreferences.storeString(ID_PARTNER, partner?.idPartner)
        sharedPreferences.storeString(EMAIL_PARTNER, partner?.emailPartner)
        sharedPreferences.storeString(IMAGE_PARTNER, partner?.imagePartner)

//        sharedPreferences.storeBoolean(RELIGION, true)

    }

    fun getRegistrationDataForUser(): RegistrationData {
        return RegistrationData(
            id = id,
            name = name.value,
            email = email.value,
            imageUser = imageUser,
            gender = intGender.value,
            ageGroup = intAge.value,
            token = token.value,
            invitationCode = invitationCode.value,
            baseCode = invitationCode.value,
            typeOfUser = typeOfUser.value ?: "",
            codeUsed = false,
            numberSupporters = 0,
            hasPartner = false,
            currentTaskDay = 1,
        )
    }

    fun getRegistrationDataForCaregiver(): RegistrationData {
        return RegistrationData(
            id = id,
            name = name.value,
            email = email.value,
            imageUser = imageUser,
            token = token.value,
            typeOfUser = typeOfUser.value ?: "",
            hasPartner = true,
            partner = partner,
            permissions = Permissions(),
            status = 1
        )
    }
}
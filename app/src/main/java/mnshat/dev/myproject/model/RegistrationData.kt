package mnshat.dev.myproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.BASE_CODE
import mnshat.dev.myproject.util.CODE_USED
import mnshat.dev.myproject.util.DIALECT
import mnshat.dev.myproject.util.GENDER
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.INVITATION_CODE
import mnshat.dev.myproject.util.IS_LOGGED
import mnshat.dev.myproject.util.NUMBER_SUPPORTERS
import mnshat.dev.myproject.util.RELIGION
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.TYPE_OF_USER
import mnshat.dev.myproject.util.USER
import mnshat.dev.myproject.util.USER_NAME
import mnshat.dev.myproject.util.log

@Parcelize
data class RegistrationData(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var gender: Int? = null,
    var ageGroup: Int? = null,
    var dialect: Int? = null,
    var token: String? = null,
    var invitationCode: String? = null,
    var typeOfUser: String? = null,
    var codeUsed: Boolean? = null,
    var supports: List<String>? = null,
    var gratitudeList: MutableList<Gratitude>? = null,
    val permissions: Permissions? = null,
    var numberSupporters: Int? = null,
    var hasPartner: Boolean? = null,
    var baseCode: String? = null,
    var status: Int? = null,
    var idPartner: String? = null,
    var emailPartner: String? = null,
    var namePartner: String? = null,
    var currentTaskDay: Int? = null
): Parcelable {
    fun storeDataLocally(sharedPreferences: SharedPreferencesManager) {
        sharedPreferences.storeString(TYPE_OF_USER, typeOfUser)
        sharedPreferences.storeString(USER_NAME, name)
        sharedPreferences.storeBoolean(HAS_PARTNER, hasPartner)
        sharedPreferences.storeInt(GENDER, gender)
        sharedPreferences.storeInt(AGE_GROUP, ageGroup)
        sharedPreferences.storeInt(DIALECT, dialect)
        sharedPreferences.storeBoolean(IS_LOGGED, true)

        if (permissions != null) {
        } else {
            sharedPreferences.storeString(INVITATION_CODE, invitationCode)
            sharedPreferences.storeBoolean(CODE_USED, codeUsed)
            sharedPreferences.storeString(BASE_CODE, baseCode)
            sharedPreferences.storeInt(NUMBER_SUPPORTERS, numberSupporters)
        }
    }

    override fun toString(): String {
        return "RegistrationData(" +
                "name=$name, " +
                "email=$email, " +
                "password=$password," +
                " gender=$gender, " +
                "ageGroup=$ageGroup," +
                " token=$token, " +
                "typeOfUser=$typeOfUser, " +
                "invitationCode=$invitationCode" +
                ")"
    }

    fun clearData() {
        id = null
        name = null
        email = null
        password = null
        gender = null
        ageGroup = null
        token = null
        invitationCode = null
        typeOfUser = USER
        codeUsed = false
        supports = null
        numberSupporters = null
        hasPartner = null
        baseCode = null
    }
}

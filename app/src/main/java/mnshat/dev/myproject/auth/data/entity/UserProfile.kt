
package mnshat.dev.myproject.auth.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import mnshat.dev.myproject.model.Gratitude
import mnshat.dev.myproject.model.Partner
import mnshat.dev.myproject.model.Permissions
import mnshat.dev.myproject.users.caregiver.tools.cofe.domain.model.UserIdea
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.BASE_CODE
import mnshat.dev.myproject.util.CODE_USED
import mnshat.dev.myproject.util.EMAIL_PARTNER
import mnshat.dev.myproject.util.GENDER
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.ID_PARTNER
import mnshat.dev.myproject.util.IMAGE_PARTNER
import mnshat.dev.myproject.util.INVITATION_CODE
import mnshat.dev.myproject.util.IS_LOGGED
import mnshat.dev.myproject.util.NAME_PARTNER
import mnshat.dev.myproject.util.NUMBER_SUPPORTERS
import mnshat.dev.myproject.util.RELIGION
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.TYPE_OF_USER
import mnshat.dev.myproject.util.USER
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.USER_IMAGE
import mnshat.dev.myproject.util.USER_NAME

@Parcelize
data class UserProfile(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var imageUser: String? = null,
    var gender: Int? = null,
    var ageGroup: Int? = null,
    var religion : Boolean? = null,
    var token: String? = null,
    var invitationCode: String? = null,
    var typeOfUser: String? = null,
    var isInvitationUsed: Boolean? = null,
    var numberSupporters: Int? = null,
    var hasPartner: Boolean? = null,
    var invitationBase: String? = null,
    var status: Int? = null,

): Parcelable {

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
        isInvitationUsed = false
        numberSupporters = null
        hasPartner = null
        invitationBase = null
    }
}

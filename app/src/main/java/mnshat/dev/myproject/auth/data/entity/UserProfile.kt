
package mnshat.dev.myproject.auth.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import mnshat.dev.myproject.util.USER

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
    var typeOfUser: String? = null,
    var invitationUsed: Boolean? = null,
    var invitationBase: String? = null,
    var invitationCode: String? = null,

    var supportersNumber: Int? = null,
    var hasPartner: Boolean? = null,
    var status: Int? = null,
    var currentDay:Int? = null,

    ): Parcelable {


    override fun toString(): String {
        return "UserProfile(" +
                "name=$name, " +
                "email=$email, " +
                "password=$password," +
                " gender=$gender, " +
                "ageGroup=$ageGroup," +
                "supportersNumber=$supportersNumber," +
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
        invitationUsed = false
        supportersNumber = null
        hasPartner = null
        invitationBase = null
    }
}


package mnshat.dev.myproject.auth.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import mnshat.dev.myproject.util.USER

@Parcelize
data class UserProfile(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var partnerEmail: String? = null,
    var partnerId: String? = null,
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
    var allowDailyProgramDetails: Boolean? = null,
    var allowMoodTrackingDetails: Boolean? = null,
    var allowPrivateMessages: Boolean? = null,

    ): Parcelable {

    fun updateData(key: String, value: Any?){
        when(key){
            "imageUser" -> imageUser = value.toString()
            "name" -> name = value.toString()
            "gender" -> gender = value as Int?
            "ageGroup" -> ageGroup = value as Int?
            "religion" -> religion = value as Boolean?
        }
    }

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
                "invitationUsed=$invitationUsed" +
                ")"
    }

}

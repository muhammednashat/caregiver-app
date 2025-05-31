package mnshat.dev.myproject.users.caregiver.tools.cofe.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserIdea(
    val idea:String? =null,
    val response:String? = null,
): Parcelable

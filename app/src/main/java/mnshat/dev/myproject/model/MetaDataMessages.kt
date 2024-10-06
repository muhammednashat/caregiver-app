package mnshat.dev.myproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class MetaDataMessages(
    val nameCaregiver:String? = null,
    val idCaregiver:String? = null,
    val imageCaregiver:String = "",

    val namePatient:String? = null,
    val idPatient:String? = null,
    val imagePatient:String = "",
): Parcelable


package mnshat.dev.myproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Supplication(
    var name: String,
    var number: Int)
    :Parcelable

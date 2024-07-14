package mnshat.dev.myproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gratitude(
    val question: String = "",
    val answer: String = ""
) : Parcelable

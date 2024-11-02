package mnshat.dev.myproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gratitude(
    val index: Int = 0,
    val answer: String = "",
    val timeStamp: Long = System.currentTimeMillis(),
) : Parcelable

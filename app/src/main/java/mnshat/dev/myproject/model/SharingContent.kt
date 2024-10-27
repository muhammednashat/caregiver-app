package mnshat.dev.myproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize

data class SharingContent(
    val type: String?=null,
    val supplication: Supplication?=null,
    val libraryContent:LibraryContent?=null,
    val gratitude: Gratitude?=null,
    val supporters:List<String>? = null,
    val timeStamp: Long? = System.currentTimeMillis(),
): Parcelable

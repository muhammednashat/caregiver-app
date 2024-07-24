package mnshat.dev.myproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Supplication(val name: String, val number: Int):Parcelable

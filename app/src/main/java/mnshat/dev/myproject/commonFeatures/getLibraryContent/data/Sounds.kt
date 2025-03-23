package mnshat.dev.myproject.commonFeatures.getLibraryContent.data

import android.content.Context
import mnshat.dev.myproject.R

data class Sound(
    val  name: String,
    val sound: Int
)

fun getSoundsList(context: Context) =
listOf(
    Sound(context.getString(R.string.sound_rain) , R.raw.sea),
    Sound(context.getString(R.string.sound_sea) , R.raw.sea),
)

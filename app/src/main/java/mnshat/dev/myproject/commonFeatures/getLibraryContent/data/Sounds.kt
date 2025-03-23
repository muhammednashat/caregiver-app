package mnshat.dev.myproject.commonFeatures.getLibraryContent.data

import android.content.Context
import mnshat.dev.myproject.R

data class Sound(
    val  name: String,
    val sound: Int,
    val image: Int
)

fun getSoundsList(context: Context) =
listOf(
    Sound(context.getString(R.string.sound_sea) ,  R.raw.sea,R.drawable.img_sea ),
    Sound(context.getString(R.string.sound_rain) , R.raw.rain , R.drawable.image_rain),
)

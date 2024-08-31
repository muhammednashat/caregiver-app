package mnshat.dev.myproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Content (
    val id:Int,
    val date:String,
    val category:String,
    val arText:String,
    val enText:String,
    val arTitle:String,
    val enTitle:String,
    val type:String,
    val rate:Int,
    val viewCount:Int,
    val imageURL:String,
    val videoURL:String,
    val audioURL:String,

) : Parcelable {
}
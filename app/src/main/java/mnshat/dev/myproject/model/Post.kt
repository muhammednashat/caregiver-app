package mnshat.dev.myproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import mnshat.dev.myproject.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.users.patient.tools.gratitude.entity.Gratitude


@Parcelize
data class Post(
    var type: String?=null,
    var supplication: Supplication?=null,
    val libraryContent: LibraryContent?=null,
    val gratitude: Gratitude?=null,
    val supporters:List<String>? = null,
    val timeStamp: Long? = System.currentTimeMillis(),
): Parcelable{

}

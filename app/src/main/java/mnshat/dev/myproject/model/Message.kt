package mnshat.dev.myproject.model

data class Message(
    val text: String? = null,
    val senderId: String? = null,
    val timeStamp: Long? = System.currentTimeMillis(),
//    val photoUrl: String? = null,
//    val imageUrl: String? = null,
)

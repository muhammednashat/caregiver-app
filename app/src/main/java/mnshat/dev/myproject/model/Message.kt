package mnshat.dev.myproject.model

data class Message(
    val text: String? = null,
    val senderId: String? = null,
    val timeStamp: String? = System.currentTimeMillis().toString(),
//    val photoUrl: String? = null,
//    val imageUrl: String? = null,
)

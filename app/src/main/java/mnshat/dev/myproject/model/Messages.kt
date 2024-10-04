package mnshat.dev.myproject.model

data class Messages(
    val namePartner:String? = null,
    val idPartner:String? = null,
    val urlImagePartner:String = "",
    var messages:MutableList<Message> = mutableListOf()
)

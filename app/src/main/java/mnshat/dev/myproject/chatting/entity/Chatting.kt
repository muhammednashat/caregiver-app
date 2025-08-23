package mnshat.dev.myproject.chatting.entity

data class Chatting(
    val meta: MetaDataMessages? = null,
    var messages: MutableList<Message>? = mutableListOf()
)
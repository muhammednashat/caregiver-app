package mnshat.dev.myproject.model

data class Messages(

    val meta: MetaDataMessages? = null,
    var messages: MutableList<Message>? = mutableListOf()
)

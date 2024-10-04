package mnshat.dev.myproject.interfaces

import android.view.View
import mnshat.dev.myproject.model.Supplication

interface ItemMessagesListClicked {

    fun onItemClicked(name: String, idPartner:String, urlImage:String)
}
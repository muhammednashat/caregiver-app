package mnshat.dev.myproject.interfaces

import mnshat.dev.myproject.model.Supplication

interface ItemSupplicationClicked {

    fun onItemClicked(supplication: Supplication)
}
package mnshat.dev.myproject.interfaces

import android.view.View
import mnshat.dev.myproject.model.Supplication

interface ItemSupplicationClicked {

    fun onItemClicked(view: View, supplication: Supplication)
}
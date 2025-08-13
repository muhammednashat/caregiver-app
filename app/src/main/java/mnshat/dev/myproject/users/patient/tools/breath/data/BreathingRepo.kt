package mnshat.dev.myproject.users.patient.tools.breath.data

import android.content.Context
import androidx.annotation.UiContext
import mnshat.dev.myproject.R
import mnshat.dev.myproject.users.patient.tools.breath.model.getSoundsList
import mnshat.dev.myproject.util.data.getListOfDurations

class BreathingRepo {


    fun listOfDurations(context: Context) = getListOfDurations(context)

    fun listOfSounds(context: Context) = getSoundsList(context)



}
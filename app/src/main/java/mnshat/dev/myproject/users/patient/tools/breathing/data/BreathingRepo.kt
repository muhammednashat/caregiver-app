package mnshat.dev.myproject.users.patient.tools.breathing.data

import android.content.Context
import mnshat.dev.myproject.users.patient.tools.breathing.model.getSoundsList
import mnshat.dev.myproject.util.data.getListOfDurations

class BreathingRepo {
    fun listOfDurations(context: Context) = getListOfDurations(context)
    fun listOfSounds(context: Context) = getSoundsList(context)
}
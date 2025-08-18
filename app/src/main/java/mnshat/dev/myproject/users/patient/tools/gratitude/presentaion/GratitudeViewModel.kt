package mnshat.dev.myproject.users.patient.tools.gratitude.presentaion

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.users.patient.tools.gratitude.entity.Gratitude
import mnshat.dev.myproject.users.patient.tools.gratitude.data.GratitudeRepo
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GratitudeViewModel @Inject constructor(
     val  gratitudeRepo: GratitudeRepo,
) : ViewModel() {


    val gratitudeList = gratitudeRepo.gratitudeList

    private var _selectedPosition:Int=0
    fun getSelectedPosition() = _selectedPosition
    fun getRandomQuestion(context: Context): String {
        val questions = gratitudeRepo.getGratitudeQuestionsList(context)
        val randomNumber = Random.nextInt(questions.size.minus(1))
        _selectedPosition = randomNumber
        val question = questions[randomNumber]
        return question
    }

    fun getQuestion(context: Context, index: Int): String {
        val questions = gratitudeRepo.getGratitudeQuestionsList(context)
        val question = questions[index]
        return question
    }

    fun setSelectedPosition(randomNumber: Int) {
        _selectedPosition = randomNumber
    }

     fun saveGratitudeRemotely(gratitude: Gratitude, callBack:(Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                gratitudeRepo.saveGratitudeRemotely(gratitude)
                callBack(true)
            } catch (e: Exception) {
                callBack(false)
            }
        }
    }
    fun retrieveGratitudeListRemotely() {
        viewModelScope.launch {
            try {
              gratitudeRepo.retrieveGratitudeListRemotely()
            } catch (e: Exception) {
                gratitudeList.value = emptyList()
            }
        }
    }



}
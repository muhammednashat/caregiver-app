package mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Inject

@HiltViewModel

class CofeViewModel @Inject constructor(
    val sharedPreferences: SharedPreferencesManager
):ViewModel(){


    var cupNumber = 0

    private val _textIdea = MutableLiveData<String?>()
    var textIdea: MutableLiveData<String?> = _textIdea


    private val _userAdjustedText = MutableLiveData<String?>()
    var userAdjustedText: MutableLiveData<String?> = _userAdjustedText

    private val _textQuestion1 = MutableLiveData<String?>()
    var textQuestion1: MutableLiveData<String?> = _textQuestion1

    private val _textQuestion2 = MutableLiveData<String?>()
    var textQuestion2: MutableLiveData<String?> = _textQuestion2

    private val _textQuestion3 = MutableLiveData<String?>()
    var textQuestion3: MutableLiveData<String?> = _textQuestion3

    private val _textQuestion4 = MutableLiveData<String?>()
    var textQuestion4: MutableLiveData<String?> = _textQuestion4

    private val _textQuestion5 = MutableLiveData<String?>()
    var textQuestion5: MutableLiveData<String?> = _textQuestion5


    fun setTextIdea(text: String) {
        _textIdea.value = text

    }

    fun clearData(){
        _textIdea.value = null
        _userAdjustedText.value = null
        _textQuestion1.value = null
        _textQuestion2.value = null
        _textQuestion3.value = null
        _textQuestion4.value = null
        _textQuestion5.value = null
        cupNumber = 0

    }


    fun isAllQuestionsAnswered() =
             _textQuestion1.value != null
              && _textQuestion2.value != null
              && _textQuestion3.value != null
              && _textQuestion4.value != null
              && _textQuestion5.value != null





}
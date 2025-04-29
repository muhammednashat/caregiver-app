package mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mnshat.dev.myproject.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel

class CofeViewModel @Inject constructor():ViewModel(){

    private val _textIdea = MutableLiveData<String>()
    var textIdea: MutableLiveData<String> = _textIdea

    private val _textQuestion1 = MutableLiveData<String>()
    var textQuestion1: MutableLiveData<String> = _textQuestion1

    private val _textQuestion2 = MutableLiveData<String>()
    var textQuestion2: MutableLiveData<String> = _textQuestion2

    var cupNumber = 0


    

}
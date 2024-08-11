package mnshat.dev.myproject.users.patient.main.tools.breath

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.model.Duration
import mnshat.dev.myproject.util.SharedPreferencesManager

class BreathViewModel(
    private val sharedPreferences: SharedPreferencesManager,
    application: Application
) : BaseViewModel(sharedPreferences, application) {

    private var _selectedPosition:Int=0

    fun getSelectedPosition() = _selectedPosition


    private val  _currentDuration = MutableLiveData<String>()
    val currentDuration: LiveData<String>
        get() = _currentDuration

    private val _progressState = MutableLiveData<Long>()
    val progressState: LiveData<Long>
        get() = _progressState

    private val _isTimerRunning = MutableLiveData<Boolean>()
    val isTimerRunning: LiveData<Boolean>
        get() = _isTimerRunning

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean>
        get() = _showDialog

    private val _remainingTime = MutableLiveData<String>()
    val remainingTime: LiveData<String>
        get() = _remainingTime

    private var countdownTimer: CountDownTimer? = null


    fun setCurrentDuration(index:Int){
        if (index != _selectedPosition) {
            setSelectedPosition(index)
        }
        _currentDuration.value = getListOfDurations()[index].text
    }

    fun setSelectedPosition(index: Int) {
        _selectedPosition = index
    }


    fun onStartButtonClicked() {
        if (_isTimerRunning.value == true) {
            _showDialog.value = true
        } else {
            startCountdown(getSelectedDurationInMillis())
        }
    }


    private fun getSelectedDurationInMillis(): Long {
        val duration = getListOfDurations()[_selectedPosition].duration
        return duration * 60 * 1000L
    }


    private fun startCountdown(durationInMillis: Long) {
        _progressState.value = durationInMillis
        countdownTimer?.cancel() // Cancel any existing timer

        countdownTimer = object : CountDownTimer(durationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _progressState.value = millisUntilFinished
                val secondsRemaining = millisUntilFinished / 1000
                _remainingTime.value = String.format(
                    "%02d:%02d",
                    secondsRemaining / 60,
                    secondsRemaining % 60
                )
            }

            override fun onFinish() {
                _progressState.value = 0
                _remainingTime.value = "00:00"
            }
        }.start()

        _isTimerRunning.value = true
    }

    fun cancelCountdown() {
        countdownTimer?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        countdownTimer?.cancel()
    }


    fun getListOfDurations(): List<Duration> {
        return listOf(
            Duration(1,"1.00 دقيقه / دقائق"),
            Duration(2,"2.00 دقيقه / دقائق"),
            Duration(3,"3.00 دقيقه / دقائق"),
            Duration(4,"4.00 دقيقه / دقائق"),
            Duration(5,"5.00 دقيقه / دقائق"),
            Duration(6,"6.00 دقيقه / دقائق"),
            Duration(7,"7.00 دقيقه / دقائق"),
            Duration(8,"8.00 دقيقه / دقائق"),

            )
    }
}
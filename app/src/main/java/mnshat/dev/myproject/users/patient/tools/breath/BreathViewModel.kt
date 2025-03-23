package mnshat.dev.myproject.users.patient.tools.breath

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mnshat.dev.myproject.base.BaseViewModel2
import mnshat.dev.myproject.model.Duration
import mnshat.dev.myproject.util.SharedPreferencesManager

class BreathViewModel(
    private val sharedPreferences: SharedPreferencesManager,
    application: Application
) : BaseViewModel2(sharedPreferences, application) {

    private var _selectedPosition:Int=0
    private var counter :Int = 0

    fun getSelectedPosition() = _selectedPosition


    private val  _currentDuration = MutableLiveData<String>()
    val currentDuration: LiveData<String>
        get() = _currentDuration


    private val _progressState = MutableLiveData<Long?>()
    val progressState: LiveData<Long?>
        get() = _progressState

    private val _isTimerRunning = MutableLiveData<Boolean>()
    val isTimerRunning: LiveData<Boolean>
        get() = _isTimerRunning

    private val _resetProgress = MutableLiveData<Boolean>()
    val resetProgress: LiveData<Boolean>
        get() = _resetProgress

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean>
        get() = _showDialog

    private val _remainingTime = MutableLiveData<Int>()
    val remainingTime: LiveData<Int>
        get() = _remainingTime

     val soundId = MutableLiveData<Int?>()

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
       counter = 0
        if (_isTimerRunning.value == true) {
            _showDialog.value = true
        } else {
            startCountdown(getSelectedDurationInMillis())
        }
    }


     fun getSelectedDurationInMillis(): Long {
        val duration = getListOfDurations()[_selectedPosition].duration
        println(duration)
        return 1 * 60 * 1000L
    }

fun  updateSound (id:Int?){
    soundId.value = id
}
    private fun startCountdown(selectedDurationInMillis: Long) {
        counter++  // counter = counter + 1  counter=3
        _progressState.value =  selectedDurationInMillis
        countdownTimer?.cancel()

        countdownTimer = object : CountDownTimer( selectedDurationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _progressState.value = millisUntilFinished
                calcRemainingTime(millisUntilFinished)
            }

            override fun onFinish() {
                resetProgress()
                resetRemainingTime()
                _progressState.value = 0
                if (counter < getListOfDurations()[_selectedPosition].duration){
                    startCountdown(getSelectedDurationInMillis())
                }
            }
        }.start()
        _isTimerRunning.value = true
    }

    private fun calcRemainingTime(millisUntilFinished: Long) {
        val secondsRemaining = (millisUntilFinished / 1000).toInt()
        _remainingTime.value = secondsRemaining
    }

    fun cancelCountdown() {
        countdownTimer?.cancel()
    }
    fun resetIsTimerRunning() {
        _isTimerRunning.value = false
    }

    override fun onCleared() {
        super.onCleared()
        countdownTimer?.cancel()
    }

    fun gitDurationAsPhases(): List<Int> {
        val durationInSeconds= getSelectedDurationInMillis().div(1000).toInt()
        val phase = durationInSeconds.div(4)
        return listOf(
            durationInSeconds.minus(1),
            durationInSeconds.minus(phase).minus(1),
            durationInSeconds.minus(phase).minus(phase).minus(1),
            durationInSeconds.minus(phase).minus(phase).minus(phase).minus(1),
            durationInSeconds.minus(phase).minus(phase).minus(phase).minus(phase).minus(1),
        )
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

    fun resetProgress() {
        _resetProgress.value = true
    }
    fun resetRestProgress(){
        _resetProgress.value = false
    }
    fun resetRemainingTime(){
        _remainingTime.value = 0
    }
    fun restShowDialog() {
        _showDialog.value = false

    }

    fun clearData(){
        cancelCountdown()
        resetRemainingTime()
        resetIsTimerRunning()
        _progressState.value = null

    }


}
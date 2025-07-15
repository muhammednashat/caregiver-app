package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.LayoutTaskBinding
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.Task
import mnshat.dev.myproject.util.TextToSpeechUtil

@AndroidEntryPoint

class SpiritualFragment : BaseDailyProgramFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = LayoutTaskBinding.inflate(inflater, container, false)
        setupClickListener()
        initializeViews()
        return binding.root
    }

    fun initializeViews() {
        textToSpeech =  TextToSpeechUtil(TextToSpeech(requireActivity(), null))

//        viewModel.currentDay.value.let {
//            viewModel.listOfTasks = it?.dayTask?.spiritual as List<Task>
////            if ( viewModel.listOfTasks.size > 1) binding.btnRecommend.visibility = View.VISIBLE
//
//            getTaskFromList(viewModel.status.currentIndexSpiritual!!, 2)
//            changeColorStatus()
//        }
    }

    fun setupClickListener() {

        binding.play.setOnClickListener {
            if(textToSpeech.textToSpeech.isSpeaking){
                textToSpeech.textToSpeech.stop()
                binding.play.setImageResource(R.drawable.icon_stop_sound)
            }else{
                textToSpeech.speakText(htmlText)
                binding.play.setImageResource(R.drawable.icon_play_sound)

            }
        }

        binding.icBack.setOnClickListener{

          findNavController().popBackStack()
        }
        binding.stationDescription.setOnClickListener {
            showDescriptionDialog(R.drawable.icon_descriptionw,
                getString(R.string.a_whiff_of_faith),getString(R.string.a_whiff_of_faith))
        }
        binding.btnNext.setOnClickListener {
                updateStatus()
        }
        binding.btnPrevious.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.icPause.setOnClickListener {
            showTemporallyDialog(
                getString(R.string.do_you_want_to_pause_this_task_temporarily),
                getString(R.string.you_can_exit_this_task_and_come_back_again_whatever_you_want),
                R.drawable.ic_timer,
                getString(R.string.pause_task_temporarily)
            ) {
                requireActivity().finish()
            }

        }
        binding.btnRecommend.setOnClickListener {
            val currentIndex = getNextTask(viewModel.status.currentIndexSpiritual!!, 3)
            viewModel.status.currentIndexSpiritual = currentIndex
            viewModel.updateCurrentTaskLocally()
        }
    }


    private fun changeColorStatus() {
        if (viewModel.status.educational == 1) binding.line1.setBackgroundColor(Color.parseColor("#6db7d3"))

        changeColorOfTaskImage(2, binding.constraintTask2, binding.imageTask2)
        changeColorOfTaskImage(viewModel.status.behavioral, binding.constraintTask3, binding.imageTask3)
        changeColorOfTaskImage(viewModel.status.educational, binding.constraintTask1, binding.imageTask1)
    }

    private fun updateStatus() {
        if (viewModel.status.spiritual != 1) {
            updateStatusData()
        }
        findNavController().navigate(R.id.action_spiritualFragment_to_activityFragment)
    }

    private fun updateStatusData() {
        viewModel.status.spiritual = 1
        viewModel.updateCompletionRate()
        showToast(getString(R.string.the_second_task_was_completed_successfully))
    }
    override fun onStop() {
        super.onStop()
        player?.pause()
        if (viewModel._isSyncNeeded.value == true){
            viewModel.updateCurrentTaskRemotely()
            viewModel._isSyncNeeded.value = false
        }
    }
}

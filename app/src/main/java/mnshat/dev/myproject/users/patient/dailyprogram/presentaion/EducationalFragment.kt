package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import android.app.AlertDialog
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
import mnshat.dev.myproject.util.TextToSpeechUtil

@AndroidEntryPoint
class EducationalFragment : BaseDailyProgramFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutTaskBinding.inflate(inflater, container, false)
        hideSpiritualIcon(binding.constraintTask2, binding.line1)
        binding.btnPrevious.visibility = View.GONE
        checkInternetConnection()
        return  binding.root
    }

    private fun checkInternetConnection() {
        if (isConnected()) {
            init()
        } else {
            showNoInternetDialog()
        }
    }

    private fun showNoInternetDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.no_internet_connection))
            .setMessage(getString(R.string.please_check_your_internet_connection_and_try_again))
            .setPositiveButton(getString(R.string.try_again)) { dialog, _ ->
                checkInternetConnection()
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }


    private fun init() {
        showDescriptionDialog(R.drawable.icon_lamp,
            getString(R.string.a_step_towards_change),getString(R.string.a_step_towards_change))

        textToSpeech = TextToSpeechUtil(TextToSpeech(requireActivity(), null))
        viewModel.initTasksList("educational")
        initializeViews()
        setupClickListener()
    }


     fun initializeViews() {
         if (viewModel.listOfTasks.size > 1) binding.btnRecommend.visibility = View.VISIBLE
         getTaskFromList(viewModel.status.currentIndexEducational!!, 2)
         changeColorStatus()
    }


   private  fun setupClickListener() {

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
           activity?.finish()
       }

        binding.btnNext.setOnClickListener {
            updateStatus()
        }



        binding.btnRecommend.setOnClickListener {
            val currentIndex = getNextTask(viewModel.status.currentIndexEducational!!, 1)
            viewModel.status.currentIndexEducational = currentIndex
            viewModel.updateCurrentTaskLocally()
        }
    }


    private fun changeColorStatus() {
        if (viewModel.status.educational == 1) binding.line1.setBackgroundColor(Color.parseColor("#6db7d3"))
        changeColorOfTaskImage(2, binding.constraintTask1, binding.imageTask1)
        changeColorOfTaskImage(viewModel.status.spiritual, binding.constraintTask2, binding.imageTask2)
        changeColorOfTaskImage(viewModel.status.behavioral, binding.constraintTask3, binding.imageTask3)

    }

    private fun updateStatus() {
        if (viewModel.status.educational != 1){
            updateStatusData()
        }
        navigateToNextTask()
    }

    private fun updateStatusData() {
        viewModel.status.educational = 1
        viewModel.updateCompletionRate()
        showToast(getString(R.string.the_first_task_was_completed_successfully))
   }



    private fun navigateToNextTask() {

        if (viewModel.userProfile.religion!!) {
            findNavController().navigate(R.id.action_educationalFragment_to_spiritualFragment)
        } else {
            findNavController().navigate(R.id.action_educationFragment_to_behaviorActivationFragment)
        }
    }





}


package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButton.OnCheckedChangeListener
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.DialogDoCompleteTaskBinding
import mnshat.dev.myproject.databinding.LayoutTaskBinding
import mnshat.dev.myproject.users.patient.moodTracking.presentaion.PostMoodTrackingActivity
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.TextToSpeechUtil

@AndroidEntryPoint
class BehaviouralActivationFragment : BaseDailyProgramFragment(),
    SuggestedChallengesFragment.OnTaskItemClickListener {

    private var currentStatus: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutTaskBinding.inflate(inflater, container, false)
        checkInternetConnection()
        return binding.root
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
        textToSpeech =  TextToSpeechUtil(TextToSpeech(requireActivity(), null))
        viewModel.initTasksList("behaviorActivation")
        if (viewModel.listOfTasks.size > 1) binding.btnRecommend.visibility = View.VISIBLE
        witchFragment("BehaviouralActivationFragment")
        getTaskFromList(viewModel.status.currentIndexBehavioral!!, 2)
        changeColorStatus()
        hideSpiritualIcon(binding.constraintTask2, binding.line2)
        setupClickListener()

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

        binding.stationDescription.setOnClickListener {
            showDescriptionDialog(R.drawable.icon_descriptionww,
                getString(R.string.time_of_adventure),getString(R.string.time_of_adventure))
        }

        binding.btnNext.setOnClickListener {
            if (viewModel.status.behavioral != 1){
                showDialogAskingForCompletion()
            }else{
                startActivity(Intent(requireContext(), PostMoodTrackingActivity::class.java))
                activity?.finish()
            }
        }



        binding.btnRecommend.setOnClickListener {
            showSuggestedChallengesFragment()

        }
        binding.btnPrevious.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun  showSuggestedChallengesFragment(){

        val suggestedChallengesFragment = SuggestedChallengesFragment.newInstance(
            this,
            viewModel.status.currentIndexBehavioral!!,
            viewModel.listOfTasks,
            viewModel.sharedPreferences.getString(LANGUAGE)
        )
        suggestedChallengesFragment.show(
            childFragmentManager,
            SuggestedChallengesFragment::class.java.name
        )
    }

    private fun changeColorStatus() {
        if (viewModel.status.educational == 1) binding.line1.setBackgroundColor(Color.parseColor("#6db7d3"))
        if (viewModel.status.behavioral == 1) binding.line2.setBackgroundColor(Color.parseColor("#6db7d3"))


        changeColorOfTaskImage(2,binding.constraintTask3,binding.imageTask3)
        changeColorOfTaskImage(viewModel.status.educational,binding.constraintTask1, binding.imageTask1)
        changeColorOfTaskImage(viewModel.status.spiritual,binding.constraintTask2, binding.imageTask2)

    }

    private fun updateStatus(boolean: Boolean) {
        if (boolean) {
            updateStatusData()
        }
        startActivity(Intent(requireContext(), PostMoodTrackingActivity::class.java))
        activity?.finish()
    }

    private fun updateStatusData() {
        viewModel.status.behavioral = 1
        viewModel.updateCompletionRate()
    }

    private fun showDialogAskingForCompletion(){
       currentStatus = true
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogDoCompleteTaskBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(true)
        val window = dialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.icClose.setOnClickListener{
            dialog.dismiss()

        }
        dialogBinding.btNext.setOnClickListener{
            if (currentStatus){
                dialog.dismiss()
                updateStatus(currentStatus)
            }else{
                showToast(getString(R.string.you_will_not_go_to_the_next_day))
            }

        }
        dialogBinding.textBackToContinue.setOnClickListener{
            dialog.dismiss()
        }

        dialogBinding.radioGroup.setOnCheckedChangeListener( object: OnCheckedChangeListener,
            RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(button: MaterialButton?, isChecked: Boolean) {

            }

            override fun onCheckedChanged(radioGroup: RadioGroup?, id: Int) {
               when(id){
                   R.id.radioButtonYes -> changeStateOfMassage(dialogBinding,true)
                   R.id.radioButtonNo -> changeStateOfMassage(dialogBinding,false)
               }

            }

        })
        dialog.show()
    }


    private fun changeStateOfMassage(binding: DialogDoCompleteTaskBinding,status:Boolean) {
        currentStatus = status
        if (status){
            binding.textView.text = getString(R.string.well_done_you_will_get_a_star_when_you_complete_each_task_so_you_will_get_a_full_mark)
            binding.textView.setTextColor(Color.parseColor("#197ea5"))
            binding.imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_clap))
            binding.constraintLayout.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#e7f8ff"))
        }else{
        binding.textView.text = getString(R.string.you_will_not_go_to_the_next_day)
        binding.textView.setTextColor(Color.parseColor("#d77c3f"))
        binding.imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_danger))
        binding.constraintLayout.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#f9f2e1"))
       }
    }

    override fun onTaskItemClicked(position: Int) {
        getTaskFromList(position, 2)
        viewModel.status.currentIndexBehavioral = position
        viewModel.updateCurrentTaskLocally()
    }


}
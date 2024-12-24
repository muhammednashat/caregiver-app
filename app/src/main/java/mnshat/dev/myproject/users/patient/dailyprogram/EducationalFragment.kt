package mnshat.dev.myproject.users.patient.dailyprogram

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.DialogConfirmLogoutBinding
import mnshat.dev.myproject.databinding.DialogPreMoodSelectionBinding
import mnshat.dev.myproject.databinding.DialogStartProgramBinding
import mnshat.dev.myproject.databinding.LayoutTaskBinding
import mnshat.dev.myproject.factories.DailyProgramViewModelFactory
import mnshat.dev.myproject.model.Task
import mnshat.dev.myproject.users.patient.calender.presentaion.ChooseDayFragment
import mnshat.dev.myproject.users.patient.moodTracking.presentaion.OnNextClickListener
import mnshat.dev.myproject.users.patient.moodTracking.presentaion.PreMoodSelectionFragment
import mnshat.dev.myproject.users.patient.moodTracking.presentaion.PreMoodSelectionFragment2
import mnshat.dev.myproject.util.RELIGION

class EducationalFragment : BaseDailyProgramFragment<LayoutTaskBinding>(), OnNextClickListener {

    override fun getLayout() = R.layout.layout_task

    override fun initializeViews() {
        showDailyProgram2()
        initViewModel()

        binding.btnPrevious.visibility = View.GONE

        viewModel.currentTask.let {
            viewModel.listOfTasks = it.dayTask?.educational as List<Task>
            if ( viewModel.listOfTasks.size == 1) binding.btnRecommend.visibility = View.GONE
            getTaskFromList(viewModel.status.currentIndexEducational!!, 2)
            changeColorStatus()
        }
        hideSpiritualIcon(binding.constraintTask2, binding.line1)
    }

    private fun initViewModel() {
        val factory = DailyProgramViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[DailyProgramViewModel::class.java]
    }

    private fun showDailyProgram2() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogPreMoodSelectionBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false)

        val window = dialog.window
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.8).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }

        dialogBinding.icClose.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.button.setOnClickListener {
            dialog.dismiss()

            findNavController().navigate(R.id.action_educationalFragment_to_postDailyProgramFragment)
//            findNavController().navigate(R.id.action_educationalFragment_to_preMoodSelectionFragment)
//            val bottomFragment =  PreMoodSelectionFragment2()
//            bottomFragment.setOnNextClickListener(this)
//            bottomFragment.show(childFragmentManager, PreMoodSelectionFragment2::class.java.name)
        }

        dialog.show()
    }


    override fun setupClickListener() {
        binding.btnNext.setOnClickListener {
            updateStatus()
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
        updateCompletionRate()
        showToast(getString(R.string.the_first_task_was_completed_successfully))
   }



    private fun navigateToNextTask() {
        if (sharedPreferences.getBoolean(RELIGION)) {
            findNavController().navigate(R.id.action_educationalFragment_to_spiritualFragment)
        } else {
            findNavController().navigate(R.id.action_educationFragment_to_behaviorActivationFragment)
        }
    }
    private fun showStartDailyProgram() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogStartProgramBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(true)

        val window = dialog.window
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.8).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }

        dialogBinding.icClose.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.button.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    override fun onStop() {
        super.onStop()
        player?.pause()
        if (viewModel._isSyncNeeded.value == true){
            viewModel.updateCurrentTaskRemotely()
            viewModel._isSyncNeeded.value = false
        }
    }

    override fun onNextClicked() {
        showStartDailyProgram()
    }

}


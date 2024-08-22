package mnshat.dev.myproject.users.patient.dailyprogram

import android.graphics.Color
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.LayoutTaskBinding
import mnshat.dev.myproject.factories.DailyProgramViewModelFactory
import mnshat.dev.myproject.model.Task

class SpiritualFragment : BaseDailyProgramFragment<LayoutTaskBinding>() {

    override fun getLayout() = R.layout.layout_task

    override fun initializeViews() {
        val factory = DailyProgramViewModelFactory(sharedPreferences,activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[DailyProgramViewModel::class.java]

        viewModel.currentTask.let {
            viewModel.listOfTasks = it.dayTask?.spiritual as List<Task>
            if ( viewModel.listOfTasks.size == 1) binding.btnRecommend.visibility = View.GONE
            getTaskFromList(viewModel.status.currentIndexBehavior!!, 2)
            changeColorStatus()
        }
    }

    override fun setupClickListener() {
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
            val currentIndex = getNextTask(viewModel.status.currentIndexBehavior!!, 3)
            viewModel.status.currentIndexBehavior = currentIndex
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
        updateCompletionRate()
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

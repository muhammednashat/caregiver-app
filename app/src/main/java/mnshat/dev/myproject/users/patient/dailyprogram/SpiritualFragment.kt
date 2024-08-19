package mnshat.dev.myproject.users.patient.dailyprogram

import android.graphics.Color
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.LayoutTaskBinding
import mnshat.dev.myproject.factories.DailyProgramViewModelFactory
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.Task
import mnshat.dev.myproject.util.RELIGION

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
            if (validation()) {
                updateStatus()
            }
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
        if (viewModel.status.contemplation == 1) binding.line1.setBackgroundColor(Color.parseColor("#6db7d3"))
        if (viewModel.status.activity == 1) binding.line2.setBackgroundColor(Color.parseColor("#6db7d3"))

        changeColorOfTaskImage(2, binding.constraintTask3, binding.imageTask3)
        changeColorOfTaskImage(viewModel.status.activity, binding.constraintTask2, binding.imageTask2)
        changeColorOfTaskImage(viewModel.status.contemplation, binding.constraintTask1, binding.imageTask1)
    }



    private fun validation(): Boolean {
        if (viewModel.status.contemplation != 1 || viewModel.status.activity != 1) {
            showToast(getString(R.string.you_must_complete_all_previous_tasks))
            return false
        }
        return true
    }


    private fun updateStatus() {
        showProgressDialog()
        if (viewModel.status.behaviorOrSpiritual != 1) {
            viewModel.status.remaining = viewModel.status.remaining?.minus(1)
            viewModel.status.completionRate = viewModel.status.completionRate?.plus(30)
            viewModel.status.behaviorOrSpiritual = 1
            viewModel.updateCurrentTaskLocally()
        }
        retrieveNextDay(viewModel.status.day?.plus(1).toString())
    }


    private fun retrieveNextDay(day: String?) {
        viewModel.retrieveTaskDayFromDatabase(
            day!!, FirebaseService.userEmail!!, FirebaseService.userId!!, sharedPreferences
        ) {
            findNavController().navigate(R.id.action_behaviorOrSpiritualFragment_to_activityFragment)
            dismissProgressDialog()
        }
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

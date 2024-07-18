package mnshat.dev.myproject.users.patient.dailyprogram

import android.graphics.Color
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.LayoutTaskBinding
import mnshat.dev.myproject.factories.DailyProgramViewModelFactory
import mnshat.dev.myproject.model.Task

class ContemplationFragment : BaseDailyProgramFragment<LayoutTaskBinding>() {

    override fun getLayout() = R.layout.layout_task

    override fun initializeViews() {
        val factory = DailyProgramViewModelFactory(sharedPreferences,activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[DailyProgramViewModel::class.java]
        binding.btnPrevious.visibility = View.GONE
        viewModel.currentTask.let {
            viewModel.listOfTasks = it.dayTask?.contemplation as List<Task>
            if ( viewModel.listOfTasks.size == 1) binding.btnRecommend.visibility = View.GONE
            getTaskFromList(viewModel.status.currentIndexContemplation!!, 2)
            changeColorStatus()
        }
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
            val currentIndex = getNextTask(viewModel.status.currentIndexContemplation!!, 1)
            viewModel.status.currentIndexContemplation = currentIndex
            viewModel.updateCurrentTaskLocally()
        }



    }

    private fun changeColorStatus() {

        if (viewModel.status.contemplation == 1) binding.line1.setBackgroundColor(Color.parseColor("#6db7d3"))
        changeColorOfTaskImage(2, binding.constraintTask1, binding.imageTask1)
        changeColorOfTaskImage(viewModel.status.activity, binding.constraintTask2, binding.imageTask2)
        changeColorOfTaskImage(
            viewModel.status.behaviorOrSpiritual,
            binding.constraintTask3,
            binding.imageTask3
        )

    }

    private fun updateStatus() {
        if (viewModel.status.contemplation != 1){
            viewModel.status.remaining = viewModel.status.remaining?.minus(1)
            viewModel.status.completionRate = viewModel.status.completionRate?.plus(30)
            viewModel.status.contemplation = 1
            showToast(getString(R.string.the_first_task_was_completed_successfully))
            viewModel.updateCurrentTaskLocally()
        }
        findNavController().navigate(R.id.action_contemplationFragment_to_activityFragment)
    }


}


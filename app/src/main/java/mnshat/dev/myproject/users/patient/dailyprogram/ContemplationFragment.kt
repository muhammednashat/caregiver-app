package mnshat.dev.myproject.users.patient.dailyprogram

import android.view.View
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.LayoutTaskBinding
import mnshat.dev.myproject.model.Task

class ContemplationFragment : BaseDailyProgramFragment<LayoutTaskBinding>() {

    override fun getLayout() = R.layout.layout_task

    override fun initializeViews() {

        _currentTask = getCurrentTask()!!
        listOfTasks = _currentTask.dayTask?.contemplation as List<Task>
        status = _currentTask.status!!

        if (listOfTasks.size == 1) binding.btnRecommend.visibility = View.GONE
        getTaskFromList(status.currentIndexContemplation!!, 1)
        binding.btnPrevious.visibility = View.GONE
        setStatusColor()
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
            val currentIndex = getNextTask(status.currentIndexContemplation!!, 1)
            status.currentIndexContemplation = currentIndex
            updateCurrentTaskLocally()
        }



    }

    private fun setStatusColor() {
//        if (status.contemplation == 0) {
//            changeColorOfTaskImage(2, binding.constraintTask1, binding.imageTask1)
//        } else {
//            changeColorOfTaskImage(1, binding.constraintTask1, binding.imageTask1)
//        }

        changeColorOfTaskImage(2, binding.constraintTask1, binding.imageTask1)
        changeColorOfTaskImage(status.activity, binding.constraintTask2, binding.imageTask2)
        changeColorOfTaskImage(
            status.behaviorOrSpiritual,
            binding.constraintTask3,
            binding.imageTask3
        )

    }

    private fun updateStatus() {
        if (status.contemplation != 1){
            status.remaining = status.remaining?.minus(1)
            status.completionRate = status.completionRate?.plus(30)
            status.contemplation = 1
            showToast(getString(R.string.the_first_task_was_completed_successfully))
            updateCurrentTaskLocally()
        }
        findNavController().navigate(R.id.action_contemplationFragment_to_activityFragment)
    }


}


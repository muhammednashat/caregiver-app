package mnshat.dev.myproject.maybeneedlater

import android.view.View
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.LayoutTaskBinding
import mnshat.dev.myproject.model.Task
import mnshat.dev.myproject.users.patient.dailyprogram.BaseDailyProgramFragment
import mnshat.dev.myproject.util.CURRENT_TASK

class SpiritualFragment : BaseDailyProgramFragment<LayoutTaskBinding>() {

    override fun getLayout() = R.layout.layout_task

    override fun initializeViews() {
        _currentTask = getCurrentTask()!!
        listOfTasks = _currentTask.dayTask?.spiritual as List<Task>
        if (listOfTasks.size == 1) binding.btnRecommend.visibility = View.GONE
        changeColorStatus()
        updateStatus()
    }
    override fun setupClickListener() {
        binding.btnNext.setOnClickListener{
        }
        binding.btnPrevious.setOnClickListener{
            findNavController().popBackStack()
        }
    }
    private fun changeColorStatus() {
        status = _currentTask.status!!
        if (status.behaviorOrSpiritual == 0) {
//            changeColorOfTaskImage(1, binding.task3)
        } else {
//            changeColorOfTaskImage(status.behaviorOrSpiritual, binding.task3)
        }
//        changeColorOfTaskImage(status.contemplation, binding.task1)
//        changeColorOfTaskImage(status.activity, binding.task2)
//        changeColorOfTaskImage(status.gratitude, binding.task4)
    }

    private fun updateStatus() {
        status.behaviorOrSpiritual = 1
        sharedPreferences.storeObject(CURRENT_TASK, _currentTask)
    }





}

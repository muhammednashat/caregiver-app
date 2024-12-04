package mnshat.dev.myproject.users.patient.calender.presentaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.databinding.FragmentDayPlanBinding
import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity

@AndroidEntryPoint
class DayPlanFragment : BaseFragment() {

    private lateinit var binding: FragmentDayPlanBinding
    private lateinit var adapter: TasksAdapter
    private val viewModel: CalenderViewModel by viewModels()
    private var done = 0
    private var taskSize = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDayPlanBinding.inflate(inflater, container, false)
        getTasks("Thu Dec 19 00:00:00 GMT+02:00 2024")
        observing()
        return  binding.root
    }

    private fun getTasks(day: String) {
        viewModel.getTasks(day)
    }

    private fun observing() {
        viewModel.taskList.observe(viewLifecycleOwner) { tasks ->
            tasks?.let {
                initViews(it)
                setUpRecyclerView(it)
            }
        }
    }

    private fun initViews(tasks: List<TaskEntity>) {
        taskSize= tasks.size
        binding.numTasks.text = taskSize.toString()
        for (task in tasks) {
            if (task.isCompleted) {
                done++
            }
        }
        updateUi(done)
    }

    private fun updateUi(done: Int) {
        binding.numDone.text = done.toString()
        val progress = (3.toFloat() / taskSize.toFloat()) * 100
        binding.circularProgress.progress = progress.toInt()
    }


    private fun setUpRecyclerView(tasks: List<TaskEntity>) {
        adapter = TasksAdapter(tasks)
        binding.recyclerView.adapter = adapter
    }



}
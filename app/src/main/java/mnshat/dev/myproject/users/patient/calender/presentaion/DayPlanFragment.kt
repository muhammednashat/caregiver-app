package mnshat.dev.myproject.users.patient.calender.presentaion

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentDayPlanBinding
import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity
import mnshat.dev.myproject.util.log

@AndroidEntryPoint
class DayPlanFragment : BaseFragment(), OnItemClickListener {

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
        getTasks(viewModel.getDayEntity().day)
        done = 0
        observing()
        isItToday()
        setUpListeners()

        return  binding.root
    }

    private fun isItToday() {
        if (viewModel.getPickedDate().isBefore(viewModel.today)) {
            binding.addButton.visibility = View.GONE
            log("yesterday")
        } else if (viewModel.getPickedDate().isAfter(viewModel.today)) {
            log("tomorrow")
        } else {
            log("today")
        }
    }

    private fun setUpListeners() {
      binding.addButton.setOnClickListener {
          val action = DayPlanFragmentDirections.actionDayPlanFragmentToCreateOwnActivityFragment("updating")
         findNavController().navigate(action)
      }
        binding.back.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    private fun getTasks(day: String) {
        viewModel.getTasks(day)
    }

    private fun observing() {
        viewModel.taskList.observe(viewLifecycleOwner) { tasks ->
            if (tasks.isNotEmpty()){
                initViews(tasks)
                setUpRecyclerView(tasks)
            }
            tasks?.let {

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
        log(done.toString() + " updateUi")
        binding.numDone.text = done.toString()
        val progress = (done.toFloat() / taskSize.toFloat()) * 100
        binding.circularProgress.progress = progress.toInt()
    }


    private fun setUpRecyclerView(tasks: List<TaskEntity>) {
        adapter = TasksAdapter(tasks.toMutableList(),this)
        binding.recyclerView.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun updateTaskStatus(task: TaskEntity) {
        if (task.isCompleted) { done++}else{ done--}
        updateUi(done)
        viewModel.updateTask(task)
    }

    override fun deleteTask(taskId: Int) {
        viewModel.deleteTask(taskId)
        done = 0
        taskSize = 0
        getTasks(viewModel.getDayEntity().day)

    }


    // ToDO  From Chat GPT
    private val swipeToDeleteCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            adapter.removeItem(position)
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            val itemView = viewHolder.itemView
            if (dX < 0) { // Swiping left
                // Load delete icon drawable
                val deleteIcon = ContextCompat.getDrawable(recyclerView.context, R.drawable.baseline_delete_24)!!
                val intrinsicWidth = deleteIcon.intrinsicWidth
                val intrinsicHeight = deleteIcon.intrinsicHeight
                val iconMargin = (itemView.height - intrinsicHeight) / 2

                // Calculate icon position
                val iconLeft = itemView.right - iconMargin - intrinsicWidth
                val iconRight = itemView.right - iconMargin
                val iconTop = itemView.top + iconMargin
                val iconBottom = iconTop + intrinsicHeight

                // Set bounds and draw the icon
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                deleteIcon.draw(c)
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onStop() {
        super.onStop()
            viewModel.clearData()
    }

}
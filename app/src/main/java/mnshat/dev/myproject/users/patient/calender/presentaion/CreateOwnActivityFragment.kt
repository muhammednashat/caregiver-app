package mnshat.dev.myproject.users.patient.calender.presentaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCreateOwnActivityBinding
import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity


@AndroidEntryPoint
class CreateOwnActivityFragment : BaseFragment() {

    private lateinit var binding: FragmentCreateOwnActivityBinding
    private val viewModel: CalenderViewModel by viewModels()
    private var activityName: String = ""
    private var activityDescription: String = ""
    private var flag: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateOwnActivityBinding.inflate(inflater, container, false)
        val args = CreateOwnActivityFragmentArgs.fromBundle(requireArguments())
        flag = args.flag
        setListener()
        return binding.root

    }

    private fun validateInputFields(): Boolean {
        activityName = binding.activityNameField.text.toString().trim()
        activityDescription = binding.activityDescriptionField.text.toString().trim()

        if (activityName.isEmpty()) {
            binding.activityNameField.error = getString(R.string.activity_name_is_required)
            binding.activityNameField.requestFocus()
            return false
        }

        return true
    }


    private fun setListener() {

        binding.createButton.setOnClickListener {
            if (validateInputFields()) {
                if (flag == "updating") {
                    addNewTask()
                }else{
                    createDayPlay()
                }

            }


        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun createDayPlay() {
        val dayEntity = viewModel.getDayEntity()
        val activities = viewModel.getChosenActivities()
        val tasks = viewModel.toTaskEntities(activities, dayEntity.day).toMutableList()
        tasks.add(task())
        viewModel.createDayPlan(dayEntity, tasks)
    }

    private fun addNewTask() {
        viewModel.updateTask(task())
        findNavController().popBackStack()
    }

    private fun task() = TaskEntity(
        day = viewModel.getDayEntity().day,
        nameTask = activityName,
        description = activityDescription,
        image = R.drawable.ic_plan_day,
        isCompleted = false,
    )




}
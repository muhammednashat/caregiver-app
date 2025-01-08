package mnshat.dev.myproject.users.patient.calender.presentaion

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.DialogCalenderBinding
import mnshat.dev.myproject.databinding.FragmentCreateOwnActivityBinding
import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity


@AndroidEntryPoint
class CreateOwnActivityFragment : BaseFragment() {

    private lateinit var binding: FragmentCreateOwnActivityBinding
    private val viewModel: CalenderViewModel by viewModels()
    private var activityName: String = ""
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
        showDoneDialog()
    }

    private fun addNewTask() {
        viewModel.updateTask(task())
        findNavController().popBackStack()
    }

    private fun task() = TaskEntity(
        day = viewModel.getDayEntity().day,
        nameTask = activityName,
        description = "",
        image = R.drawable.ic_plan_day,
        isCompleted = false,
    )

    private fun showDoneDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogCalenderBinding.inflate(layoutInflater)
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

        dialogBinding.button.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
            dialog.dismiss()
        }

        dialog.show()
    }


}
package mnshat.dev.myproject.users.patient.dailyprogram

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButton.OnCheckedChangeListener
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.DialogDoCompleteTaskBinding
import mnshat.dev.myproject.databinding.LayoutTaskBinding
import mnshat.dev.myproject.model.Task

class ActivityFragment  : BaseDailyProgramFragment<LayoutTaskBinding>() {


    override fun getLayout() = R.layout.layout_task
    override fun initializeViews() {

        _currentTask = getCurrentTask()!!
        listOfTasks = _currentTask.dayTask?.activity as List<Task>
        status = _currentTask.status!!

        if (listOfTasks.size == 1) binding.btnRecommend.visibility = View.GONE
        getTaskFromList(status.currentIndexActivity!!,2)
        changeColorStatus()
    }


    override fun setupClickListener() {

        binding.btnNext.setOnClickListener {

            if (status.activity != 1){
                showDialogAskingForCompletion()
            }else{
                findNavController().navigate(R.id.action_activityFragment_to_behaviorOrSpiritualFragment)
            }

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
            val currentIndex=  getNextTask(status.currentIndexActivity!!,2)
            status.currentIndexActivity = currentIndex
            updateCurrentTaskLocally()

        }
        binding.btnPrevious.setOnClickListener {
            findNavController().popBackStack()
        }

    }
    private fun changeColorStatus() {

//        if (status.activity == 0) {
//            changeColorOfTaskImage(2,binding.constraintTask2,binding.imageTask2)
//        } else {
//            changeColorOfTaskImage(1,binding.constraintTask2,binding.imageTask2)
//        }

        changeColorOfTaskImage(2,binding.constraintTask2,binding.imageTask2)
        changeColorOfTaskImage(status.contemplation,binding.constraintTask1, binding.imageTask1)
        changeColorOfTaskImage(status.behaviorOrSpiritual,binding.constraintTask3, binding.imageTask3)

    }

    private fun updateStatus() {
            status.remaining = status.remaining?.minus(1)
            status.completionRate = status.completionRate?.plus(40)
            status.activity = 1
            updateCurrentTaskLocally()
    }

    private fun showDialogAskingForCompletion(){

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogDoCompleteTaskBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(true)
        val window = dialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBinding.icClose.setOnClickListener{
            dialog.dismiss()

        }
        dialogBinding.btNext.setOnClickListener{
            updateStatus()
        }
        dialogBinding.textBackToContinue.setOnClickListener{
            dialog.dismiss()
        }
        dialogBinding.radioGroup.setOnCheckedChangeListener( object: OnCheckedChangeListener,
            RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(button: MaterialButton?, isChecked: Boolean) {


            }

            override fun onCheckedChanged(radioGroup: RadioGroup?, id: Int) {
               when(id){
                   R.id.radioButtonYes -> changeStateOfMassage(dialogBinding,true)
                   R.id.radioButtonNo -> changeStateOfMassage(dialogBinding,false)
               }

            }

        })
        dialog.show()
    }

    private fun changeStateOfMassage(binding: DialogDoCompleteTaskBinding,status:Boolean) {

        if (status){
            binding.textView.text = getString(R.string.well_done_you_will_get_a_star_when_you_complete_each_task_so_you_will_get_a_full_mark)
            binding.textView.setTextColor(Color.parseColor("#197ea5"))
            binding.imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_clap))
            binding.constraintLayout.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#e7f8ff"))
        }else{
        binding.textView.text = getString(R.string.you_will_not_go_to_the_next_day)
        binding.textView.setTextColor(Color.parseColor("#d77c3f"))
        binding.imageView.setImageDrawable(resources.getDrawable(R.drawable.ic_danger))
        binding.constraintLayout.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#f9f2e1"))
       }
    }


}
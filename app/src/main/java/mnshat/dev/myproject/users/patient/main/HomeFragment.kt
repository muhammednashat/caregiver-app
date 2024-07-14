package mnshat.dev.myproject.users.patient.main

import android.content.Intent
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentUserHomeBinding
import mnshat.dev.myproject.model.CurrentTask
import mnshat.dev.myproject.users.patient.dailyprogram.DailyProgramActivity
import mnshat.dev.myproject.util.USER_NAME

class HomeFragment : BaseUserFragment<FragmentUserHomeBinding>() {


    override fun getLayout() = R.layout.fragment_user_home
    override fun initializeViews() {
        binding.nameUser.text = sharedPreferences.getString(USER_NAME)
    }

    override fun onStart() {
        setStatusOfCurrentTask(getCurrentTask())
        super.onStart()
    }



    override fun setupClickListener() {

        binding.btnContinue.setOnClickListener {
            startActivity(Intent(requireActivity(), DailyProgramActivity::class.java))
        }
    }



    fun setStatusOfCurrentTask(currentTask: CurrentTask?){
        currentTask?.let {
            val status= it.status
            binding.currentDay.text= getString(R.string.day,status?.day)
            binding.currentLevel.text= getString(R.string.current_level,status?.currentLevel)
            binding.tasksRemaining.text= getString(R.string.tasks_remaining,status?.remaining)
           val completionRate = status?.completionRate!!
            binding.seekBar.progress = completionRate
            binding.seekBar.setOnTouchListener(object: OnTouchListener{
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                   return true
                }

            })
            binding.progress.text=  "$completionRate%"
            if (status.contemplation == 0){
                binding.btnContinue.text= getString(R.string.start)
            }else{
                binding.btnContinue.text= getString(R.string.continue1)
            }

            changeColorOfTaskImage(status.contemplation,binding.constraintTask1, binding.imageTask1)
             if (status.contemplation == 1) binding.line1.setBackgroundColor(Color.parseColor("#6db7d3"))
            changeColorOfTaskImage(status.activity,binding.constraintTask2,binding.imageTask2)
            if (status.activity == 1) binding.line2.setBackgroundColor(Color.parseColor("#6db7d3"))
            changeColorOfTaskImage(status.behaviorOrSpiritual,binding.constraintTask3, binding.imageTask3)
    }
    }
}
package mnshat.dev.myproject.users.patient.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentUserHomeBinding
import mnshat.dev.myproject.factories.PatientViewModelFactory
import mnshat.dev.myproject.model.CurrentTask
import mnshat.dev.myproject.users.patient.dailyprogram.DailyProgramActivity
import mnshat.dev.myproject.util.USER_NAME
import mnshat.dev.myproject.util.createContentFake

class HomeFragment : BasePatientFragment<FragmentUserHomeBinding>() {

    private lateinit var viewModel:PatientViewModel

    override fun getLayout() = R.layout.fragment_user_home

    override fun initializeViews() {
        binding.nameUser.text = sharedPreferences.getString(USER_NAME)
        hideSpiritualIcon(binding.constraintTask2, binding.line1)

    }

    override fun onStart() {
        setStatusOfCurrentTask(getCurrentTask())
        super.onStart()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        iitViewModel()
        super.onActivityCreated(savedInstanceState)
    }

    private fun iitViewModel() {
        val factory = PatientViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[PatientViewModel::class.java]
        binding.lifecycleOwner = this
        observeViewModel()
    }


    private fun observeViewModel(){
        binding.viewModel = viewModel

        viewModel.toolsClick.observe(viewLifecycleOwner){

            if (it){
                findNavController().navigate(R.id.action_patientHomeFragment_to_mainAzcarFragment)
                viewModel.restToolsClick()
            }
        }

        viewModel.continueClick.observe(viewLifecycleOwner){
            if (it){
                startActivity(Intent(requireActivity(), DailyProgramActivity::class.java))
                viewModel.restContinueClick()
            }
        }

        viewModel.educationalContentClicked.observe(viewLifecycleOwner){
//            createContentFake()
            if (it){
                findNavController().navigate(R.id.action_patientHomeFragment_to_educationalContentFragment)
                viewModel.restEducationalContentClicked()
            }

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
            if (status.educational == 0){
                binding.btnContinue.text= getString(R.string.start)
            }else{
                binding.btnContinue.text= getString(R.string.continue1)
            }

            changeColorOfTaskImage(status.educational,binding.constraintTask1, binding.imageTask1)
             if (status.educational == 1) binding.line1.setBackgroundColor(Color.parseColor("#6db7d3"))
            changeColorOfTaskImage(status.spiritual,binding.constraintTask2,binding.imageTask2)
            if (status.behavioral == 1) binding.line2.setBackgroundColor(Color.parseColor("#6db7d3"))
            changeColorOfTaskImage(status.behavioral,binding.constraintTask3, binding.imageTask3)
    }
    }
}
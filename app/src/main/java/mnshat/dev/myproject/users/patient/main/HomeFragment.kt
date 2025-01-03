package mnshat.dev.myproject.users.patient.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion.LibraryActivity
import mnshat.dev.myproject.databinding.FragmentUserHomeBinding
import mnshat.dev.myproject.factories.PatientViewModelFactory
import mnshat.dev.myproject.model.CurrentTask2
import mnshat.dev.myproject.users.patient.calender.presentaion.CalenderActivity
import mnshat.dev.myproject.users.patient.dailyprogram.presentaion.DailyProgramActivity
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.USER_IMAGE
import mnshat.dev.myproject.util.USER_NAME
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log

class HomeFragment : BasePatientFragment<FragmentUserHomeBinding>() {

    private lateinit var viewModel:PatientViewModel

    override fun getLayout() = R.layout.fragment_user_home

    override fun initializeViews() {
        loadImage(requireActivity(),sharedPreferences.getString(USER_IMAGE),binding.imageUser)

        binding.nameUser.text = sharedPreferences.getString(USER_NAME)
        log(sharedPreferences.getString(USER_ID) + "12344444444444444444444")
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
//                startActivity(Intent(requireActivity(), DayTaskActivity::class.java))
                startActivity(Intent(requireActivity(), DailyProgramActivity::class.java))
                viewModel.restContinueClick()
            }
        }
    }

    override fun setupClickListener() {
        super.setupClickListener()

        binding.dailyProgram.setOnClickListener() {
            startActivity(Intent(requireActivity(), LibraryActivity::class.java))
        }

        binding.helpNumbers.setOnClickListener {
            findNavController().navigate(R.id.action_patientHomeFragment_to_numberHelpingFragment2)
        }
        binding.containerDailyPlanner.setOnClickListener {
            startActivity(Intent(requireActivity(), CalenderActivity::class.java))
        }

    }


    private fun setStatusOfCurrentTask(currentTask: CurrentTask2?){
        currentTask?.let {
            val status= it.status
            binding.currentDayLevel.text= buildString {
                append(getString(R.string.day, status?.day))
                append(" ")
                append(getString(R.string.current_level, status?.currentLevel))
            }

        }
    }
}
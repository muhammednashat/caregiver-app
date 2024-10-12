package mnshat.dev.myproject.users.patient.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentUserHomeBinding
import mnshat.dev.myproject.factories.PatientViewModelFactory
import mnshat.dev.myproject.features.chatting.ChatActivity
import mnshat.dev.myproject.features.libraraycontent.LibraryActivity
import mnshat.dev.myproject.model.CurrentTask
import mnshat.dev.myproject.users.patient.dailyprogram.DailyProgramActivity
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.USER_NAME
import mnshat.dev.myproject.util.log

class HomeFragment : BasePatientFragment<FragmentUserHomeBinding>() {

    private lateinit var viewModel:PatientViewModel

    override fun getLayout() = R.layout.fragment_user_home

    override fun initializeViews() {
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
                startActivity(Intent(requireActivity(), DailyProgramActivity::class.java))
                viewModel.restContinueClick()
            }
        }

        viewModel.educationalContentClicked.observe(viewLifecycleOwner){
//            createContentFake()

            if (it){
                startActivity( Intent(requireActivity(), LibraryActivity::class.java))
                viewModel.restEducationalContentClicked()
            }

        }

    }

    override fun setupClickListener() {
        super.setupClickListener()

        binding.constraintLayout15.setOnClickListener {
            startActivity(Intent(requireActivity(), ChatActivity::class.java))
        }

    }


    private fun setStatusOfCurrentTask(currentTask: CurrentTask?){
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
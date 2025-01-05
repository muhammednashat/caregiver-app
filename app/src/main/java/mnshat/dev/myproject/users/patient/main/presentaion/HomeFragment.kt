package mnshat.dev.myproject.users.patient.main.presentaion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion.LibraryActivity
import mnshat.dev.myproject.databinding.FragmentUserHomeBinding
import mnshat.dev.myproject.users.patient.calender.presentaion.CalenderActivity
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.users.patient.dailyprogram.presentaion.DailyProgramActivity
import mnshat.dev.myproject.util.USER_IMAGE
import mnshat.dev.myproject.util.USER_NAME
import mnshat.dev.myproject.util.loadImage

@AndroidEntryPoint
class HomeFragment : BaseFragment() {


    private lateinit var binding: FragmentUserHomeBinding
    private val viewModel: PatientViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserHomeBinding.inflate(inflater,container, false)
        setupClickListener()
        initializeViews()
        observeViewModel()
        return binding.root
    }

    override fun onStart() {
        showProgressDialog()
        viewModel.getCurrentTask()
        super.onStart()
    }


    private fun observeViewModel() {
        viewModel.currentTask.observe(viewLifecycleOwner){
            it.let {
                setStatusOfCurrentTask(it)
                dismissProgressDialog()
            }
        }
    }

    fun initializeViews() {
        loadImage(requireActivity(),viewModel.sharedPreferences.getString(USER_IMAGE),binding.imageUser)
        binding.nameUser.text = viewModel.sharedPreferences.getString(USER_NAME)
    }



     fun setupClickListener() {

        binding.dailyProgram.setOnClickListener() {
            startActivity(Intent(requireActivity(), DailyProgramActivity::class.java))
        }
        binding.rootTools.setOnClickListener{
            findNavController().navigate(R.id.action_patientHomeFragment_to_mainAzcarFragment)
        }
        binding.rootEducationalContent.setOnClickListener{
            startActivity(Intent(requireActivity(), LibraryActivity::class.java))
        }
        binding.helpNumbers.setOnClickListener {
            findNavController().navigate(R.id.action_patientHomeFragment_to_numberHelpingFragment2)
        }
        binding.containerDailyPlanner.setOnClickListener {
            startActivity(Intent(requireActivity(), CalenderActivity::class.java))
        }
    }


    private fun setStatusOfCurrentTask(currentTask: CurrentDay?){
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
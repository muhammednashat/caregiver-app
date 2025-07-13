package mnshat.dev.myproject.users.patient.main.presentaion

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion.LibraryActivity
import mnshat.dev.myproject.databinding.FragmentUserHomeBinding
import mnshat.dev.myproject.users.patient.calender.presentaion.CalenderActivity
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.users.patient.dailyprogram.presentaion.DailyProgramActivity
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentUserHomeBinding
    private val viewModel: UserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserHomeBinding.inflate(inflater,container, false)
        onBoarding()
        setupClickListener()
        initializeViews()
        isPermissionGranted()
        return binding.root
    }

    private fun onBoarding() {

        if (viewModel.isUserLogged) {
            OnBoardingFragment().show(childFragmentManager,null)
            viewModel.updateLoggedStatus()
        }

    }

    override fun onStart() {
        setStatusOfCurrentTask(viewModel.currentTask())
        super.onStart()
    }



    fun initializeViews() {
        loadImage(requireActivity(),viewModel.userProfile.imageUser,binding.imageUser)
        binding.nameUser.text = viewModel.userProfile.name
    }



     fun setupClickListener() {

        binding.dailyProgram.setOnClickListener() {
            startActivity(Intent(requireActivity(), DailyProgramActivity::class.java))
        }

         binding.statistics.setOnClickListener() {
             findNavController().navigate(R.id.action_patientHomeFragment_to_trackingMoodFragment)
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


    private fun isPermissionGranted() {
        when {

            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED -> {
                log("the Permission is granted")
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), Manifest.permission.POST_NOTIFICATIONS) -> {
                log("shouldShowRequestPermissionRationale")

            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        ContextCompat.checkSelfPermission(requireActivity(),Manifest.permission.POST_NOTIFICATIONS)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
            } else {

            }
        }








}
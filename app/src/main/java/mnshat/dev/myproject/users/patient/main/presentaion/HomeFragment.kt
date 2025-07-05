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
import com.google.firebase.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion.LibraryActivity
import mnshat.dev.myproject.databinding.FragmentUserHomeBinding
import mnshat.dev.myproject.users.patient.calender.presentaion.CalenderActivity
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.users.patient.dailyprogram.presentaion.DailyProgramActivity
import mnshat.dev.myproject.util.IS_SECOND_TIME
import mnshat.dev.myproject.util.USER_IMAGE
import mnshat.dev.myproject.util.USER_NAME
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mnshat.dev.myproject.firebase.FirebaseService
import okhttp3.MediaType.Companion.toMediaType

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentUserHomeBinding
    private val viewModel: PatientViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserHomeBinding.inflate(inflater,container, false)
        onBoarding()
        setupClickListener()
//        initializeViews()
//        observeViewModel()
        isPermissionGranted()
        return binding.root
    }

    private fun onBoarding() {
        val islogged = viewModel.sharedPreferences.getBoolean(IS_SECOND_TIME)
        if (!islogged) {
            OnBoardingFragment().show(childFragmentManager,null)
            viewModel.sharedPreferences.storeBoolean(IS_SECOND_TIME,true)
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

    override fun onStart() {
//        showProgressDialog()
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


    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
            } else {

            }
        }








}
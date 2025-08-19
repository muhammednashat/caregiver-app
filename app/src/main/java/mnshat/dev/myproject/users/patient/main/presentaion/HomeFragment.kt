package mnshat.dev.myproject.users.patient.main.presentaion

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.getLibraryContent.presentaion.LibraryActivity
import mnshat.dev.myproject.databinding.DialogPreMoodSelectionBinding
import mnshat.dev.myproject.databinding.FragmentUserHomeBinding
import mnshat.dev.myproject.users.patient.calender.presentaion.CalenderActivity
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.users.patient.dailyprogram.presentaion.DailyProgramActivity
import mnshat.dev.myproject.users.patient.moodTracking.presentaion.activties.MoodTrackingActivity
import mnshat.dev.myproject.users.patient.tools.UserToolsActivity
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

        binding.dailyProgram.setOnClickListener {

            if (viewModel.currentTask().status?.preChecked!!){
            startActivity(Intent(requireActivity(), DailyProgramActivity::class.java))
            }else{
                showMoodTrackingDialog()
            }
        }

         binding.statistics.setOnClickListener() {
             findNavController().navigate(R.id.action_patientHomeFragment_to_trackingMoodFragment)
        }

        binding.rootTools.setOnClickListener{
            startActivity(Intent(requireActivity(), UserToolsActivity::class.java))
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
            }

        }
    }


    private fun showMoodTrackingDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogPreMoodSelectionBinding.inflate(layoutInflater)
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

        dialogBinding.icClose.setOnClickListener {


            dialog.dismiss()
        }
        dialogBinding.button.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(requireContext(), MoodTrackingActivity::class.java))
        }
        dialog.show()
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
    private var startTime: Long = 0 // 10

    override fun onResume() {
        super.onResume()
        startTime = System.currentTimeMillis() // 11
    }

    override fun onPause() {
        super.onPause()
        val timeSpent = System.currentTimeMillis() - startTime

        val bundle = Bundle().apply {
            putString("screen_name", "HomeScreen") // change per screen
            putLong("time_spent_ms", timeSpent)
        }

        FirebaseAnalytics.getInstance(requireContext()).logEvent("screen_time", bundle)
    }






}
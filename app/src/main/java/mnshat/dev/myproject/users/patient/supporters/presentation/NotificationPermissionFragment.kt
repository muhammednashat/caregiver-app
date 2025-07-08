package mnshat.dev.myproject.users.patient.supporters.presentation

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentNotificationPermissionBinding
import mnshat.dev.myproject.model.Permissions
import mnshat.dev.myproject.util.ENGLISH_KEY


class NotificationPermissionFragment : BaseSupporterFragment<FragmentNotificationPermissionBinding>() {
    private lateinit var permissions: Permissions

    override fun getLayout() = R.layout.fragment_notification_permission
    override fun initializeViews() {
        val args:NotificationPermissionFragmentArgs by navArgs()
        permissions = args.permission
        setupUi(permissions)
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
    }

    override fun setupClickListener() {
        binding.allNotification.setOnCheckedChangeListener {
                _, isChecked ->
            if (isChecked){
                binding.typeNotification.visibility = View.VISIBLE
            }else{
                binding.typeNotification.visibility = View.GONE
            }
        }
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


private fun setupUi(permissions: Permissions){
    permissions?.let {
        if (it.allowNotifications) {
            binding.allNotification.isChecked = true
            binding.typeNotification.visibility = View.VISIBLE
            when (it.typeNotifications){
                1 -> binding.dailyProgramCompletion.isChecked =true
                2 -> binding.moodTrackingUpdate.isChecked =true
                3 -> binding.psychologicalTestResult.isChecked =true
            }


        }
    }
}
}



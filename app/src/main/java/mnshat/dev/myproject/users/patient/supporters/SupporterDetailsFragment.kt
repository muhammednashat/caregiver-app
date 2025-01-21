package mnshat.dev.myproject.users.patient.supporters

import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentSupporterDetailsBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.STATUS


class SupporterDetailsFragment : BaseSupporterFragment<FragmentSupporterDetailsBinding>() {
    private lateinit var supporter: RegistrationData
    private lateinit var action: NavDirections

    override fun getLayout() = R.layout.fragment_supporter_details
    override fun initializeViews() {
        val args: SupporterDetailsFragmentArgs by navArgs()
        supporter = args.supporter
        setUpUiSupporter(args.supporter)
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
    }

    override fun setupClickListener() {

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.icMore.setOnClickListener {
            showPopupMenu(it)
        }

        binding.permissions.viewReporting.setOnClickListener {
            action =
                SupporterDetailsFragmentDirections.actionSupporterDetailsFragmentToSetViewReportingPermissionFragment(
                    supporter.permissions!!
                )
            findNavController().navigate(action)
        }
        binding.permissions.notification.setOnClickListener {
            action =
                SupporterDetailsFragmentDirections.actionSupporterDetailsFragmentToSetNotificationPermissionFragment(
                    supporter.permissions!!
                )
            findNavController().navigate(action)
        }

    }

    private fun setUpUiSupporter(supporter: RegistrationData) {
        binding.nameSupporter.text = supporter.name
        checkStatus()
        supporter.permissions?.let {
            if (it.allowDailyProgramDetails) binding.permissions.viewDailyProgramDetails.isChecked =
                true
            if (it.allowMoodTrackingDetails) binding.permissions.viewMoodTrackingDetails.isChecked =
                true

            if (it.allowPrivateMessages) binding.permissions.allowPrivateMessages.isChecked = true
        }
    }

    private fun checkStatus() {
        if (supporter.status == 1) {
            binding.status.background = getDrawable(requireActivity(), R.drawable.corner_light_blue)

            binding.status.text = requireActivity().getString(R.string.active)
        } else {
            binding.status.background = getDrawable(requireActivity(), R.drawable.corner_red)
            binding.status.text = requireActivity().getString(R.string.not_active)
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireActivity(), view)
        popupMenu.inflate(R.menu.settings_support_menu)
        if (supporter.status == 0) {
            popupMenu.menu.findItem(R.id.menu_suspend_temporarily).title =
                getString(R.string.remove_temporary_suspend)
        }

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_encouragement_messages -> {

                }

                R.id.menu_suspend_temporarily -> {
                    isSupporterSuspend()
                }

                else ->
                    showTemporallyDialog(
                    getString(R.string.remove_supporter),
                    getString(R.string.confirm_remove_supporter),
                        R.drawable.ic_user_profile,

                    getString(R.string.remove_supporter_confirmation),
                ){}
            }
            true
        }
        popupMenu.show()
    }

    private fun isSupporterSuspend() {
        if (supporter.status == 0) {
            showTemporallyDialog(
                getString(R.string.remove_temporary_suspend),
                getString(R.string.confirm_remove_temporary_suspend),
                R.drawable.ic_user_profile,

                getString(R.string.remove_temporary_suspend),
            ){
                changeStatusOfSupporter(1)
            }
        } else {

            showTemporallyDialog(
                getString(R.string.suspend_temporarily),
                getString(R.string.confirm_temporarily_suspend_supporter),
                R.drawable.ic_user_profile,

                getString(R.string.suspend_temporarily)

            ){
                changeStatusOfSupporter(0)
            }
        }

    }

    private fun changeStatusOfSupporter(status: Int?) {
        val updateData = mutableMapOf(
            STATUS to status,
        )

        FirebaseService.updateItemsProfileUser(supporter.id!!, updateData) {
            if (it) {
                supporter.status = status
                checkStatus()
            } else {

            }
            dismissProgressDialog()
        }
    }

}
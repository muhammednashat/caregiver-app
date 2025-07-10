package mnshat.dev.myproject.users.patient.supporters.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentSupporterDetailsBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.Permissions
import mnshat.dev.myproject.auth.data.entity.UserProfile
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.STATUS
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log

@AndroidEntryPoint
class SupporterDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentSupporterDetailsBinding
    private val viewModel: SupporterViewModel by viewModels()

    private lateinit var supporter: UserProfile
    private lateinit var action: NavDirections
    private  var currentPermissions = mutableListOf(false,false,false)
    private  val modifiedPermissions = mutableListOf(false,false,false)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSupporterDetailsBinding.inflate(inflater, container, false)
        initializeViews()
        setupClickListener()
        return binding.root
    }
     fun initializeViews() {
        val args: SupporterDetailsFragmentArgs by navArgs()
        supporter = args.supporter
        setUpUiSupporter(args.supporter)
        setUpSwitchesListener()

    }

    private fun setUpSwitchesListener() {
     binding.permissions.viewDailyProgramDetails.setOnCheckedChangeListener { _, isChecked ->updateSwitching(0,isChecked)}
     binding.permissions.viewMoodTrackingDetails.setOnCheckedChangeListener { _, isChecked ->updateSwitching(1,isChecked)}
     binding.permissions.allowPrivateMessages.setOnCheckedChangeListener { _, isChecked ->updateSwitching(2,isChecked)}
    }
    private fun updateSwitching(index:Int, isChecked:Boolean){
        modifiedPermissions[index] = isChecked
        if (currentPermissions == modifiedPermissions){
            binding.save.visibility = View.GONE
        }else{
            binding.save.visibility = View.VISIBLE
        }
    }

     fun setupClickListener() {

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.icMore.setOnClickListener {
            showPopupMenu(it)
        }
        binding.save.setOnClickListener {
            showProgressDialog()
            updateSupporterPermissionsRemotely(modifiedPermissions)
        }

//        binding.permissions.viewReporting.setOnClickListener {
//            action =
//                SupporterDetailsFragmentDirections.actionSupporterDetailsFragmentToSetViewReportingPermissionFragment(
//                    supporter.permissions!!
//                )
//            findNavController().navigate(action)
//        }
//        binding.permissions.notification.setOnClickListener {
//            action =
//                SupporterDetailsFragmentDirections.actionSupporterDetailsFragmentToSetNotificationPermissionFragment(
//                    supporter.permissions!!
//                )
//            findNavController().navigate(action)
//        }

    }

    private fun updateSupporterPermissionsRemotely(modifiedPermissions: MutableList<Boolean>) {
        val permissions = Permissions(allowDailyProgramDetails = modifiedPermissions[0] , allowMoodTrackingDetails =modifiedPermissions[1] , allowPrivateMessages= modifiedPermissions[2])
        val map = mapOf("permissions" to permissions)
        FirebaseService.updateItemsProfileUser(supporter.id!!, map) {
            if (it) {
             currentPermissions = modifiedPermissions.toMutableList()
             binding.save.visibility = View.GONE
            } else {

            }
            dismissProgressDialog()
        }
    }

    private fun setUpUiSupporter(supporter: UserProfile) {

        binding.nameSupporter.text = supporter.name
        loadImage(requireActivity(), supporter.imageUser, binding.imageUser)
        checkStatus()

            if (supporter.allowDailyProgramDetails!!){
                binding.permissions.viewDailyProgramDetails.isChecked =true
                currentPermissions[0] = true
                modifiedPermissions[0] = true
            }
            if (supporter.allowMoodTrackingDetails!!) {
                binding.permissions.viewMoodTrackingDetails.isChecked =true
                currentPermissions[1] = true
                modifiedPermissions[1] = true
            }
            if (supporter.allowPrivateMessages!!) {
                binding.permissions.allowPrivateMessages.isChecked =true
                currentPermissions[2] = true
                modifiedPermissions[2] = true
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
package mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.ChooseSupporterDialogBinding
import mnshat.dev.myproject.databinding.FragmentStep3Binding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.auth.data.entity.RegistrationData
import mnshat.dev.myproject.users.caregiver.tools.cofe.domain.model.UserIdea
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.log

@AndroidEntryPoint
class Step3Fragment : BaseFragment(), ItemListener {


    private lateinit var binding: FragmentStep3Binding
    private val viewModel: CofeViewModel by viewModels()

    private lateinit var chooseUserDialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStep3Binding.inflate(inflater)
        binding.user.enter.setOnClickListener {

            findNavController().navigate(R.id.action_step3Fragment_to_step4Fragment)

        }
        binding.friend.enter.setOnClickListener {
            retrieveUsers()
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        log("1 idea is ${viewModel.textIdea.value}")
        log("1f idea is ${viewModel.textQuestion1.value}")


        return binding.root

    }

    private fun retrieveUsers() {
        showProgressDialog()
        FirebaseService.retrieveUser(viewModel.sharedPreferences.getString(USER_ID)){
            user ->
            log("userId is ${viewModel.sharedPreferences.getString(USER_ID)}")
            user?.storeDataLocally(viewModel.sharedPreferences)
            FirebaseService.retrieveUsers(user?.supports){
                it?.let {
                    dismissProgressDialog()

                    if (it.isNotEmpty()){
                        chooseSupport(it)
                        log("users are $it")
                    }else{
                        showToast(getString(R.string.no_supporters_text))
                    }

                }
            }
        }
    }

    private fun chooseSupport(supporters: List<RegistrationData>) {

        chooseUserDialog = Dialog(requireContext())
        chooseUserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = ChooseSupporterDialogBinding.inflate(layoutInflater)
        chooseUserDialog.setContentView(dialogBinding.root)
        chooseUserDialog.setCanceledOnTouchOutside(true)

        val window = chooseUserDialog.window

        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }
        val adapter = SupportersAdapter2(supporters, this)
        dialogBinding.recyclerView.adapter = adapter
        dialogBinding.icClose.setOnClickListener {
            chooseUserDialog.dismiss()
        }

        chooseUserDialog.show()

    }

    override fun onItemClick(supporter: RegistrationData) {
        chooseUserDialog.dismiss()
        updateUserData(
            UserIdea(
                idea = viewModel.textIdea.value,
                response = "" , cupIdea = viewModel.cupNumber
            ) , supporter)

    }

    fun updateUserData(userIdea: UserIdea ,supporter: RegistrationData){

        showProgressDialog()

        val map = mapOf<String, Any>("userIdea" to userIdea)
        FirebaseService.updateItemsProfileUser(FirebaseService.userId, map) {
            if (it) {
                updateSupportData(userIdea,supporter)
            } else {
            }
            dismissProgressDialog()

        }
    }

    fun updateSupportData(userIdea: UserIdea ,supporter: RegistrationData){

        val map = mapOf<String, Any>("userIdea" to userIdea)
        FirebaseService.updateItemsProfileUser(supporter.id!!, map) {
            if (it) {
//                sendNotification(supporter.id!!,"title","body")
                val action = Step3FragmentDirections.actionStep3FragmentToFriendIdeaEditingFragment(supporter)
                viewModel.sharedPreferences.storeObject("supporter", supporter)
                viewModel.sharedPreferences.storeBoolean("isThereConnection", true)
                findNavController().navigate(action)
            } else {

            }
            dismissProgressDialog()

        }
    }
}
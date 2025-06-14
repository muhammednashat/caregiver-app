package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentSupportResponseBinding
import mnshat.dev.myproject.databinding.FriendMessageDialogBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.users.caregiver.tools.cofe.domain.model.UserIdea
import mnshat.dev.myproject.util.ID_PARTNER
import mnshat.dev.myproject.util.PARTNER_PROFILE
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log

@AndroidEntryPoint

class SupportResponseFragment : BaseFragment() {

    private val viewModel: SupportCafeViewModel by viewModels()

    private lateinit var binding: FragmentSupportResponseBinding
    private lateinit var partnerProfile: RegistrationData


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSupportResponseBinding.inflate(inflater, container, false)
         setUpListener()
         setSuggestedResponse()
          observeData()
        partnerProfile = viewModel.sharedPreferences.getObjectProfilePartner(PARTNER_PROFILE)
        loadImage(requireContext(),partnerProfile.imageUser, binding.userImage)
        return binding.root
    }

    private fun setSuggestedResponse(){
        binding.editText.setText(getString(R.string.phrase1))
    }
    private fun observeData() {
        viewModel.selectedText.observe(viewLifecycleOwner) {
          val previousText = binding.editText.text.toString()
            binding.editText.setText(previousText+" "+ it)
        }
    }

    private fun setUpListener() {
        binding.suggestedSympathy.setOnClickListener {
            SuggestedPhrasesSympathyFragment().show(childFragmentManager, "")
        }
        binding.friendIdea.setOnClickListener {
            showDialog()
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.constraintNext.setOnClickListener {
            if (binding.editText.text.toString().isNotEmpty()) {
                val idea =  viewModel.sharedPreferences.getString("userIdea")
                val cupIdea =  viewModel.sharedPreferences.getInt("cupIdea")
                val response = binding.editText.text.toString()
                val userIdea = UserIdea(response = response , idea = idea, cupIdea = cupIdea)
                updateUserData(userIdea ,viewModel.sharedPreferences.getString(ID_PARTNER))
            }else{
                showToast(getString(R.string.please_enter_your_response))
            }
        }
    }

    private fun showDialog() {
        val idea =  viewModel.sharedPreferences.getString("userIdea")
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = FriendMessageDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(true)

        val window = dialog.window
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }
        dialogBinding.close.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.text.text = idea
        dialogBinding.userName.text = partnerProfile.name
        loadImage(requireContext(),partnerProfile.imageUser, dialogBinding.imageView)
        dialog.show()
    }

    fun updateUserData(userIdea: UserIdea ,partnerId: String){

        showProgressDialog()
        val map = mapOf<String, Any>("userIdea" to userIdea)
        FirebaseService.updateItemsProfileUser(partnerId, map) {
            if (it) {
                sendNotification("0fkfkZ0WXePQQ8CdOG6XVDIeMmm2","title","body")
                updateSupportData()
            } else {
            }
            dismissProgressDialog()
        }
    }


    fun updateSupportData(){
        val map = mapOf<String, Any>("userIdea" to UserIdea())
        FirebaseService.updateItemsProfileUser(viewModel.sharedPreferences.getString(USER_ID), map) {
            if (it) {
            findNavController().navigate(R.id.action_supportResponseFragment_to_thanksFragment)
            } else {
            }
            dismissProgressDialog()

        }
    }

}
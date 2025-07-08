package mnshat.dev.myproject.users.patient.supporters.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentAddSupporterBinding
import mnshat.dev.myproject.databinding.FragmentCreateNewInvitationCodeBinding

@AndroidEntryPoint

class CreateNewInvitationCodeFragment : BaseFragment() {

    private lateinit var binding: FragmentCreateNewInvitationCodeBinding
    private val viewModel: SupporterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCreateNewInvitationCodeBinding.inflate(inflater, container, false)
//        initView()
//        setTextCode()
//        isInvitationUsed()
//        setupClickListener()
        return binding.root
    }

    private fun isValidCode(code: String): Boolean {
        return if (code.isNullOrEmpty()) {
            showToast(getString(R.string.enter_new_code))
            false
        } else if (code.length < 4) {
            showToast(getString(R.string.enter_four_digits_or_letters))
            false
        } else if (code.length > 4) {
            showToast(getString(R.string.new_code_length_exceeded))
            false
        } else {
            true
        }
    }

    private fun updateCode(userId: String?, code: String) {

//        if (userId != null) {
//            FirebaseService.userProfiles.child(userId).addListenerForSingleValueEvent(object :
//                ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()) {
//                        val updateData = hashMapOf<String, Any>(
//                            INVITATION_CODE to code,
//                            BASE_CODE to code,
//                            CODE_USED to false,
//                        )
//                        FirebaseService.userProfiles.child(userId).updateChildren(updateData)
//                            .addOnCompleteListener { task ->
//                                if (task.isSuccessful) {
//                                    showToast(getString(R.string.new_code_created_successfully))
//                                    sharedDialog.dismiss()
//                                    sharedPreferences.storeString(INVITATION_CODE, code!!)
//                                    sharedPreferences.storeString(BASE_CODE, code!!)
//                                    sharedPreferences.storeBoolean(CODE_USED, false)
//                                    setTextCode()
//
//                                } else {
//                                    val exception = task.exception
//                                    showToast("User additional info update failed: ${exception?.message}")
//                                }
//                            }
//                    }
//                    dismissProgressDialog()
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    dismissProgressDialog()
//                }
//            })
//        }

    }

    private fun dialogEditCode() {

//        sharedDialog = Dialog(requireContext())
//        sharedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        val binding = DialogEditCodeBinding.inflate(layoutInflater)
//        sharedDialog.setContentView(binding.root)
//        sharedDialog.setCanceledOnTouchOutside(true)
//        val window = sharedDialog.window
//        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        sharedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        val currentCode = sharedPreferences.getString(BASE_CODE).take(4)
//        binding.edCurrentCode.setText(currentCode)
//        binding.confirmation.setOnClickListener {
//            val code = binding.edNewCode.text.toString().trim()
//            if (isValidCode(code)) {
//                showProgressDialog()
//                val newCode = currentCode + code
//                Log.e("TAG", newCode)
//                updateCode(FirebaseService.userId, newCode)
//            }
//        }
//        binding.cancel.setOnClickListener {
//            sharedDialog.dismiss()
//        }
//        binding.icClose.setOnClickListener {
//            sharedDialog.dismiss()
//        }
//        sharedDialog.show()


    }


}
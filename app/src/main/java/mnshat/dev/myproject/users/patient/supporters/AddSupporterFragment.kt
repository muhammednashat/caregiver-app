package mnshat.dev.myproject.users.patient.supporters

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.DialogEditCodeBinding
import mnshat.dev.myproject.databinding.FragmentAddSupporterBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.util.BASE_CODE
import mnshat.dev.myproject.util.CODE_USED
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.INVITATION_CODE



class AddSupporterFragment : BaseSupporterFragment<FragmentAddSupporterBinding>() {

    override fun initializeViews() {
        FirebaseService.listenForUserDataChanges {
            it?.let {
                it.storeDataLocally(sharedPreferences)
                isCodeUsed()
            }
        }
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
        isCodeUsed()
    }

    private fun isCodeUsed() {
        setTextCode()
        if (sharedPreferences.getBoolean(CODE_USED)) {
            binding.txLabelCode.text = getString(R.string.code_already_used)
            binding.btnCopy.alpha = 0.0f
            binding.btnEditCode.alpha = 1.0f
        } else {
            binding.btnCopy.alpha = 1.0f
            binding.btnEditCode.alpha = 0.0f
        }
    }

    private fun setTextCode() {
        val baseCode = sharedPreferences.getString(BASE_CODE, "") ?: ""
        binding.edCode.setText(baseCode)
    }

    override fun getLayout() = R.layout.fragment_add_supporter

    override fun setupClickListener() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnEditCode.setOnClickListener {
            dialogEditCode()
        }
        binding.btnCopy.setOnClickListener {
            copyTextToClipboard( sharedPreferences.getString(INVITATION_CODE))
            showToast(getString(R.string.code_copied))
            findNavController().popBackStack()
        }
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
        if (userId != null) {
            FirebaseService.userProfiles.child(userId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val updateData = hashMapOf<String, Any>(
                            INVITATION_CODE to code,
                            BASE_CODE to code,
                            CODE_USED to false,
                        )
                        FirebaseService.userProfiles.child(userId).updateChildren(updateData)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    showToast(getString(R.string.new_code_created_successfully))
                                    sharedDialog.dismiss()
                                    sharedPreferences.storeString(INVITATION_CODE, code!!)
                                    sharedPreferences.storeString(BASE_CODE, code!!)
                                    sharedPreferences.storeBoolean(CODE_USED, false)
                                    setTextCode()

                                } else {
                                    val exception = task.exception
                                    showToast("User additional info update failed: ${exception?.message}")
                                }
                            }
                    }
                    dismissProgressDialog()
                }

                override fun onCancelled(error: DatabaseError) {
                    dismissProgressDialog()
                }
            })
        }
    }

    private fun dialogEditCode() {
        sharedDialog = Dialog(requireContext())
        sharedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = DialogEditCodeBinding.inflate(layoutInflater)
        sharedDialog.setContentView(binding.root)
        sharedDialog.setCanceledOnTouchOutside(true)
        val window = sharedDialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        sharedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val currentCode = sharedPreferences.getString(BASE_CODE).take(4)
        binding.edCurrentCode.setText(currentCode)
        binding.confirmation.setOnClickListener {
            val code = binding.edNewCode.text.toString().trim()
            if (isValidCode(code)) {
                showProgressDialog()
                val newCode = currentCode + code
                Log.e("TAG", newCode)
                updateCode(FirebaseService.userId, newCode)
            }
        }
        binding.cancel.setOnClickListener {
            sharedDialog.dismiss()
        }
        binding.icClose.setOnClickListener {
            sharedDialog.dismiss()
        }
        sharedDialog.show()
    }

}
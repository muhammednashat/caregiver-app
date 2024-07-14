package mnshat.dev.myproject.users.patient.editprofile

import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentEditDialectBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.util.DIALECT
import mnshat.dev.myproject.util.ENGLISH_KEY


class EditDialectFragment : BaseEditProfileFragment<FragmentEditDialectBinding>() {
    private var  currentIntDialect =0

    override fun getLayout()= R.layout.fragment_edit_dialect
    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
        currentIntDialect = sharedPreferences.getInt(DIALECT)
        setChoosenDialect(currentIntDialect)
    }

    private fun setChoosenDialect(currentIntDialect: Int) {

    }

    override fun setupClickListener() {
        binding.close.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnConfirm.setOnClickListener {
            editDialect(DIALECT, currentIntDialect)
        }
    }
    private fun editDialect(key: String, intAge: Int) {
        showProgressDialog()
        val map = mapOf<String, Any>(key to 3)
        FirebaseService.updateItemsProfileUser(FirebaseService.userId, map) {
            if (it) {
                showToast(getString(R.string.preferred_dialect_changed_successfully))
                sharedPreferences.storeInt(key, intAge!!)
                findNavController().popBackStack()
            } else {
                showToast(getString(R.string.update_failed))
            }
            dismissProgressDialog()

        }
    }



}
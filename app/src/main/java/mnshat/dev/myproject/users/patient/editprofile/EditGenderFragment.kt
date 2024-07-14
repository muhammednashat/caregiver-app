package mnshat.dev.myproject.users.patient.editprofile

import android.view.View
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentEditGenderBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.GENDER


class EditGenderFragment : BaseEditProfileFragment<FragmentEditGenderBinding>() {

    private var  currentIntGender =0

    override fun getLayout()= R.layout.fragment_edit_gender
    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
        currentIntGender = sharedPreferences.getInt(GENDER)
        changeUserUi(currentIntGender)
    }

    override fun setupClickListener() {
        binding.male.setOnClickListener {
            currentIntGender = 1
            changeUserUi(currentIntGender)
        }
        binding.female.setOnClickListener {
            currentIntGender = 2
            changeUserUi(currentIntGender)
        }
        binding.close.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnConfirm.setOnClickListener {
            editGender(GENDER, currentIntGender)
        }
    }
    private fun changeUserUi(type: Int?){
        when (type) {
            1 -> {
                binding.male.setBackgroundResource(R.drawable.corner_four_dark_blue)
                binding.maleText.setTextColor(resources.getColor(R.color.white))
                binding.female.setBackgroundResource(R.drawable.corner_four_gray)
                binding.femaleText.setTextColor(resources.getColor(R.color.dark_gray))
                binding.maleStatusCircle.visibility = View.GONE
                binding.maleStatusChecked.visibility = View.VISIBLE
                binding.femaleStatusChecked.visibility = View.GONE
                binding.femaleStatusCircle.visibility = View.VISIBLE
            }
            2 -> {
                binding.female.setBackgroundResource(R.drawable.corner_four_dark_blue)
                binding.femaleText.setTextColor(resources.getColor(R.color.white))
                binding.male.setBackgroundResource(R.drawable.corner_four_gray)
                binding.maleText.setTextColor(resources.getColor(R.color.dark_gray))
                binding.femaleStatusCircle.visibility = View.GONE
                binding.femaleStatusChecked.visibility = View.VISIBLE
                binding.maleStatusChecked.visibility = View.GONE
                binding.maleStatusCircle.visibility = View.VISIBLE
            }
        }

    }
    private fun editGender(key: String, intAge: Int) {
        showProgressDialog()
        val map = mapOf<String, Any>(key to intAge)
        FirebaseService.updateItemsProfileUser(FirebaseService.userId, map) {
            if (it) {
                showToast(getString(R.string.gender_changed_successfully))
                sharedPreferences.storeInt(key, intAge!!)
                findNavController().popBackStack()
            } else {
                showToast(getString(R.string.update_failed))
            }
            dismissProgressDialog()

        }
    }

}
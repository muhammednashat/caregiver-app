package mnshat.dev.myproject.users.patient.profile.editprofile

import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentEditAgeBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.ENGLISH_KEY

class EditAgeFragment: BaseEditProfileFragment<FragmentEditAgeBinding>() {

    private var  currentIntAge =0

    override fun getLayout()=R.layout.fragment_edit_age
    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
        currentIntAge = sharedPreferences.getInt(AGE_GROUP)
        setChoosenAge(currentIntAge)
    }

    private fun setChoosenAge(age: Int?) {
        age?.let {
            when (age) {
                1 -> binding.rbYoung.isChecked = true
                2-> binding.rbMiddleAge.isChecked = true
                3 -> binding.rbOlder.isChecked = true
            }
        }
    }

    override fun setupClickListener() {

        binding.groupRoot.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_young -> {
                    currentIntAge = 1
                }
                R.id.rb_middle_age -> {
                    currentIntAge = 2
                }
                R.id.rb_older -> {
                    currentIntAge = 3
                }
            }
        }
        binding.close.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnConfirm.setOnClickListener {
            editAgeGroup(AGE_GROUP, currentIntAge)
        }
    }


    private fun editAgeGroup(key: String, intAge: Int) {
        showProgressDialog()
        val map = mapOf<String, Any>(key to intAge)
        FirebaseService.updateItemsProfileUser(FirebaseService.userId, map) {
            if (it) {
                showToast(getString(R.string.age_group_changed_successfully))
                sharedPreferences.storeInt(key, intAge!!)
                findNavController().popBackStack()
            } else {
                showToast(getString(R.string.update_failed))
            }
            dismissProgressDialog()

        }
    }


}
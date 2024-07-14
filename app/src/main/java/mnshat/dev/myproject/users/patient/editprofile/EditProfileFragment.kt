package mnshat.dev.myproject.users.patient.editprofile

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentEditProfileBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.DIALECT
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.GENDER
import mnshat.dev.myproject.util.RELIGION
import mnshat.dev.myproject.util.USER_NAME

class EditProfileFragment : BaseEditProfileFragment<FragmentEditProfileBinding>() {
    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
        if (sharedPreferences.getBoolean(RELIGION)) {
            binding.metadata.yes.isChecked = true
        } else {
            binding.metadata.no.isChecked = true
        }
        binding.metadata.textName.text = sharedPreferences.getString(USER_NAME)
        binding.metadata.textAge.text = getTextAge(sharedPreferences.getInt(AGE_GROUP))
        binding.metadata.textGender.text = getTextGender(sharedPreferences.getInt(GENDER))
        binding.metadata.textDialect.text = getTextDialect(sharedPreferences.getInt(DIALECT))
    }

    override fun getLayout() = R.layout.fragment_edit_profile


    override fun setupClickListener() {
        binding.metadata.groupRoot.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.yes -> {
                    editReligion(RELIGION,true)
                }
                R.id.no -> {
                    editReligion(RELIGION,false)
                }
            }
        }


        binding.icBack.setOnClickListener {
//            startActivity(Intent(requireActivity(), UserScreensActivity::class.java))
            activity?.finish()
        }
        binding.metadata.name.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_changeUserNameFragment)
        }
        binding.metadata.pass.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_changePassFragment)
        }
        binding.metadata.age.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_editAgeFragment)
        }
        binding.metadata.gender.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_editGenderFragment)

        }
        binding.metadata.preferredDialect.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_editDialectFragment)
        }

    }

    private fun editReligion(key: String, needReligion: Boolean) {

        if (needReligion != sharedPreferences.getBoolean(RELIGION)){
        showProgressDialog()
        val map = mapOf<String, Any>(key to needReligion)
        FirebaseService.updateItemsProfileUser(FirebaseService.userId, map) {
            if (it) {
                showToast("تم تحديث الحالة الدينية")
                sharedPreferences.storeBoolean(key, needReligion!!)
            } else {
                showToast(getString(R.string.update_failed))
            }
            dismissProgressDialog()
        }
    }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this

    }


}
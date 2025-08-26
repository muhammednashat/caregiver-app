package mnshat.dev.myproject.users.patient.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentEditGenderBinding
import mnshat.dev.myproject.util.GENDER


class EditGenderFragment : BaseFragment() {

    private var  currentIntGender =0


    private lateinit var binding: FragmentEditGenderBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEditGenderBinding.inflate(inflater, container, false)
        initializeViews()
        setupClickListener()
        return binding.root
    }

    private fun initializeViews() {

//        currentIntGender = sharedPreferences.getInt(GENDER)
        changeUserUi(currentIntGender)
    }

    private fun setupClickListener() {
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
//        showProgressDialog()
//        val map = mapOf<String, Any>(key to intAge)
//        FirebaseService.updateItemsProfileUser(FirebaseService.userId, map) {
//            if (it) {
//                showToast(getString(R.string.gender_changed_successfully))
//                sharedPreferences.storeInt(key, intAge!!)
//                findNavController().popBackStack()
//            } else {
//                showToast(getString(R.string.update_failed))
//            }
//            dismissProgressDialog()
//
//        }
    }

}
package mnshat.dev.myproject.users.patient.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentEditAgeBinding
import mnshat.dev.myproject.util.AGE_GROUP

class EditAgeFragment: BaseFragment() {

    private var  currentIntAge =0
    private lateinit var binding: FragmentEditAgeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditAgeBinding.inflate(inflater, container, false)

        initializeViews()
        setupClickListener()
        return binding.root

    }



    private fun initializeViews() {

//        currentIntAge = sharedPreferences.getInt(AGE_GROUP)
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

    private fun setupClickListener() {

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
//        showProgressDialog()
//        val map = mapOf<String, Any>(key to intAge)
//        FirebaseService.updateItemsProfileUser(FirebaseService.userId, map) {
//            if (it) {
//                showToast(getString(R.string.age_group_changed_successfully))
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
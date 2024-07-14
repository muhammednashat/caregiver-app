package mnshat.dev.myproject.users.caregiver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mnshat.dev.myproject.databinding.FragmentCaregiverHomeBinding
import mnshat.dev.myproject.util.NAME_PARTNER

class CaregiverHomeFragment : BaseCaregiverFragment() {
lateinit var binding: FragmentCaregiverHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCaregiverHomeBinding.inflate(inflater)
         binding.namePartener.text = sharedPreferences.getString(NAME_PARTNER)
        return binding.root
    }



}
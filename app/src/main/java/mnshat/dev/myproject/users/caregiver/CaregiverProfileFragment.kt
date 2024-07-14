package mnshat.dev.myproject.users.caregiver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mnshat.dev.myproject.databinding.FragmentCaregiverProfileBinding
import mnshat.dev.myproject.util.USER_NAME


class CaregiverProfileFragment : BaseCaregiverFragment() {
    lateinit var binding: FragmentCaregiverProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCaregiverProfileBinding.inflate(inflater)
        initialize()
        setupListener()
        return binding.root

    }
    private fun initialize(){

    }
      private fun setupListener(){
          binding.logOut.setOnClickListener {
              logOut()
          }
    }
    override fun onStart() {
        binding.nameUser.text = sharedPreferences.getString(USER_NAME)
        super.onStart()
    }

}
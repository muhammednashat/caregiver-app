package mnshat.dev.myproject.users.caregiver.tools.supporterCompass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCaregiverToolsBinding
import mnshat.dev.myproject.databinding.FragmentSupporterCompassBinding
import mnshat.dev.myproject.users.caregiver.main.BaseCaregiverFragment

class SupporterCompassFragment : BaseCaregiverFragment<FragmentSupporterCompassBinding>() {

    override fun getLayout()= R.layout.fragment_supporter_compass

    override fun setupClickListener() {
        super.setupClickListener()

//        binding.imageView16.setOnClickListener {
//            findNavController().popBackStack()
//        }

    }

}
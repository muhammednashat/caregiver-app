package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCongratulationsBinding

class CongratulationsFragment : BaseFragment() {

    private lateinit var binding: FragmentCongratulationsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCongratulationsBinding.inflate(inflater, container, false)

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_congratulationsFragment2_to_postDailyProgramFragment)
        }

        return binding.root
    }

}
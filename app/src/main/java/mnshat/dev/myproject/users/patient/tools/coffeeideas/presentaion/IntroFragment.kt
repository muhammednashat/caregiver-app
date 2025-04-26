package mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentIntroBinding
import mnshat.dev.myproject.util.log
import kotlin.math.log

class IntroFragment : Fragment() {

    private lateinit var binding: FragmentIntroBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentIntroBinding.inflate(inflater)

        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_introCofe2Fragment2)
        }

        binding.back.setOnClickListener {
            activity?.finish()
        }
        return  binding.root
    }

}
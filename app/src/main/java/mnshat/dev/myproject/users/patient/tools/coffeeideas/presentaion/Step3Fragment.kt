package mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentStep2Binding
import mnshat.dev.myproject.databinding.FragmentStep3Binding

class Step3Fragment : Fragment() {


    private lateinit var binding: FragmentStep3Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStep3Binding.inflate(inflater)

        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_step3Fragment_to_step4Fragment)
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root

    }

}
package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCofePurpose2Binding


class CofePurpose2Fragment : Fragment() {

    private lateinit var binding: FragmentCofePurpose2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCofePurpose2Binding.inflate(inflater, container, false)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_cofePurpose2Fragment_to_cofeInstructionFragment)
        }

        return  binding.root
    }

}
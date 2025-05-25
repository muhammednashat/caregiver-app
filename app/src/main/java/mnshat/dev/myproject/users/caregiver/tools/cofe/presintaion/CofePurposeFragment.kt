package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mnshat.dev.myproject.R
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.databinding.FragmentCofePurposeBinding

class CofePurposeFragment : Fragment() {

    private lateinit var binding:FragmentCofePurposeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    binding = FragmentCofePurposeBinding.inflate(inflater, container, false)

        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_cofePurposeFragment_to_cofePurpose2Fragment)
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        return  binding.root
    }

}
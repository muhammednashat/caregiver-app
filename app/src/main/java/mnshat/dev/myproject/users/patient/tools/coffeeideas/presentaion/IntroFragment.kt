package mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentIntroBinding
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.util.log
import kotlin.math.log
@AndroidEntryPoint

class IntroFragment : Fragment() {

    private lateinit var binding: FragmentIntroBinding

    private val viewModel: CofeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentIntroBinding.inflate(inflater)

        binding.constraintNext.setOnClickListener {
            if (viewModel.sharedPreferences.getBoolean("isThereConnection", false)){

                val supporter = viewModel.sharedPreferences.getObjectProfilePartner("supporter", )
                val action = IntroFragmentDirections.actionIntroFragmentToFriendIdeaEditingFragment(supporter as RegistrationData)
                findNavController().navigate(action)
            }else{
                findNavController().navigate(R.id.action_introFragment_to_introCofe2Fragment2)

            }
        }

        binding.back.setOnClickListener {
            activity?.finish()
        }
        return  binding.root
    }

}
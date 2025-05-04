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
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentFriendIdeaEditingBinding
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log

@AndroidEntryPoint
class FriendIdeaEditingFragment : BaseFragment() {

    private lateinit var binding: FragmentFriendIdeaEditingBinding
    private val viewModel: CofeViewModel by viewModels()
    private lateinit var supporter: RegistrationData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentFriendIdeaEditingBinding.inflate(inflater)
        val args = FriendIdeaEditingFragmentArgs.fromBundle(requireArguments())

         supporter = args.suppurter
        loadImage(requireContext(), supporter.imageUser, binding.imageView)
        log("supporter is $supporter")
        setUpListeners()
        return  binding.root



    }
    private fun setUpListeners() {
        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_friendIdeaEditingFragment_to_step5Fragment)
        }
        binding.back.setOnClickListener {
         findNavController().popBackStack()
        }
    }

}
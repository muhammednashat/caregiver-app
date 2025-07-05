package mnshat.dev.myproject.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentMainAuthBinding

@AndroidEntryPoint
class MainAuthFragment : BaseFragment(){


    private lateinit var binding: FragmentMainAuthBinding
    private val  viewModel: AuthViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainAuthBinding.inflate(inflater)
        setupClickListener()
        return  binding.root
    }




     fun setupClickListener() {
        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_mainAuthFragment_to_signUpFragment)
        }

        binding.logIn.setOnClickListener {

//            viewModel.signUp()
            findNavController().navigate(R.id.action_mainAuthFragment_to_loginFragment)

        }
        binding.chooseLang.setOnClickListener {
            LanguageFragment().show(childFragmentManager, LanguageFragment::class.java.name)
        }
    }



}
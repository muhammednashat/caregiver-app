package mnshat.dev.myproject.auth

import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentMainAuthBinding
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.log


class MainAuthFragment : AuthBaseFragment<FragmentMainAuthBinding>() {

    override fun initializeViews() {
        log(sharedPreferences.getString(LANGUAGE) ?: "errrrr")
    }

    override fun getLayout() = R.layout.fragment_main_auth


    override fun setupClickListener() {
        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_mainAuthFragment_to_signUpFragment)
        }

        binding.logIn.setOnClickListener {
            findNavController().navigate(R.id.action_mainAuthFragment_to_loginFragment)

        }
        binding.chooseLang.setOnClickListener {
            LanguageFragment().show(childFragmentManager, LanguageFragment::class.java.name)
        }
    }



}
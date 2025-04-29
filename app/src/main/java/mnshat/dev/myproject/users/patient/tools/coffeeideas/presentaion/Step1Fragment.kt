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
import mnshat.dev.myproject.databinding.FragmentIntroCofe3Binding
import mnshat.dev.myproject.databinding.FragmentStep1Binding
import mnshat.dev.myproject.util.log

@AndroidEntryPoint
class Step1Fragment : BaseFragment() {

    private lateinit var binding: FragmentStep1Binding

    private val viewModel: CofeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStep1Binding.inflate(inflater)


        setUpListeners()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root

    }

    private fun setUpListeners() {

        binding.constraintNext.setOnClickListener {
            if (binding.editText.text.toString().isNotEmpty() && viewModel.cupNumber != 0 ) {
               log(viewModel.cupNumber.toString())
                log(viewModel.textIdea.value!!)
                findNavController().navigate(R.id.action_step1Fragment_to_step2Fragment)
            }else{
                if (binding.editText.text.toString().isEmpty()){
                    showToast(getString(R.string.please_enter_your_idea))
                }else{
                    showToast(getString(R.string.please_select_the_cup))
                }
            }

        }


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}
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
import mnshat.dev.myproject.databinding.FragmentStep2Binding
import mnshat.dev.myproject.databinding.FragmentStep4Binding
import mnshat.dev.myproject.util.log

@AndroidEntryPoint
class Step4Fragment : BaseFragment() {


    private lateinit var binding: FragmentStep4Binding
    private val viewModel: CofeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentStep4Binding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setUPListener()

        return binding.root

    }

    private fun setUPListener(){

        binding.constraintNext.setOnClickListener {

            findNavController().navigate(R.id.action_step4Fragment_to_step5Fragment)

//            if (viewModel.userAdjustedText.value.isNullOrEmpty()){
//                  log("please write your idea")
//                showToast(getString(R.string.please_write_your_idea))
//
//            }else{
//                log("next")
//                findNavController().navigate(R.id.action_step4Fragment_to_step5Fragment)
//            }

        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}
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
import mnshat.dev.myproject.databinding.FragmentStep1Binding
import mnshat.dev.myproject.databinding.FragmentStep2Binding
import mnshat.dev.myproject.util.log

@AndroidEntryPoint
class Step2Fragment : BaseFragment() {

    private lateinit var binding: FragmentStep2Binding
    private val viewModel: CofeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStep2Binding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setUpListeners()

        return binding.root

    }

    private fun setUpListeners(){

        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_step2Fragment_to_step3Fragment)

//         if (viewModel.isAllQuestionsAnswered())
//         {
//             findNavController().navigate(R.id.action_step2Fragment_to_step3Fragment)
//         }else{
//          showToast(getString(R.string.please_answer_all_questions))
//         }

        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}
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
    private var selectedCup: View? = null

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
            findNavController().navigate(R.id.action_step1Fragment_to_step2Fragment)

//            if (binding.editText.text.toString().isNotEmpty() && viewModel.cupNumber != 0) {
//                log(viewModel.cupNumber.toString())
//                log(viewModel.textIdea.value!!)
//                findNavController().navigate(R.id.action_step1Fragment_to_step2Fragment)
//            } else {
//                if (binding.editText.text.toString().isEmpty()) {
//                    showToast(getString(R.string.please_enter_your_idea))
//                } else {
//                    showToast(getString(R.string.please_select_the_cup))
//                }
//            }


        }


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.chooseCup.cup1.setOnClickListener { view ->
            updateBackground(view, 1)
        }

        binding.chooseCup.cup2.setOnClickListener { view ->
            updateBackground(view, 2)
        }

        binding.chooseCup.cup3.setOnClickListener { view ->
            updateBackground(view, 3)
        }


    }

    override fun onResume() {
        super.onResume()
        if (viewModel.cupNumber != 0) {
            val cupView = when (viewModel.cupNumber) {
                1 -> binding.chooseCup.cup1
                2 -> binding.chooseCup.cup2
                3 -> binding.chooseCup.cup3
                else -> null
            }

            cupView?.let {
                updateBackground(it, viewModel.cupNumber)
            }

        }
    }

    private fun updateBackground(view: View, cupNumber: Int) {
        log(cupNumber.toString() + "  fsdfdsf ")
        selectedCup?.setBackgroundColor(getResources().getColor(R.color.white))
        view.setBackgroundResource(R.drawable.corner_border_blue2)
        selectedCup = view
        viewModel.cupNumber = cupNumber
    }

}
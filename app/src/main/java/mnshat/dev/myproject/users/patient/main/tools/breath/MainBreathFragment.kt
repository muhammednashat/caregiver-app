package mnshat.dev.myproject.users.patient.main.tools.breath

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentMainBreathBinding
import mnshat.dev.myproject.factories.BreathViewModelFactory
import mnshat.dev.myproject.users.patient.main.BasePatientFragment


class MainBreathFragment : BasePatientFragment<FragmentMainBreathBinding>(){

    private lateinit var viewModel: BreathViewModel
    override fun initializeViews() {
        super.initializeViews()
        binding.remainingTimeFormat.text = getString(R.string.remaining_time_format,0,"ثواني متبقية")

    }


    override fun getLayout()= R.layout.fragment_main_breath


    override fun setupClickListener() {
        super.setupClickListener()

        binding.iconChooseDuration.setOnClickListener {
            ChooseDurationBreathFragment().show(
                childFragmentManager,
                ChooseDurationBreathFragment::class.java.name
            )
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        viewModel.setCurrentDuration(0)
        observeViewModel()

    }

    private fun initViewModel() {
        val factory = BreathViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[BreathViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
    private fun observeViewModel(){

    }

}
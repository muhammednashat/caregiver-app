package mnshat.dev.myproject.users.patient.main.tools.breath

import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentMainBreathBinding
import mnshat.dev.myproject.users.patient.main.BasePatientFragment


class MainBreathFragment : BasePatientFragment<FragmentMainBreathBinding>(){
    override fun initializeViews() {
        super.initializeViews()
        binding.remainingTimeFormat.text = getString(R.string.remaining_time_format,3,"ثانيه متبقية")
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

}
package mnshat.dev.myproject.commonFeatures.posts

import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentDisplaySupplicationBinding
import mnshat.dev.myproject.users.patient.main.BasePatientFragment


class DisplaySupplicationFragment : BasePatientFragment<FragmentDisplaySupplicationBinding>() {

    override fun getLayout() = R.layout.fragment_display_supplication

    override fun initializeViews() {
        super.initializeViews()

    val supplication = DisplaySupplicationFragmentArgs.fromBundle(requireArguments()).supplication
        binding.textView.text = supplication.name
    }
}
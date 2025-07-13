package mnshat.dev.myproject.commonFeatures.numbersHelping

import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.HelpingAdapterAdapter
import mnshat.dev.myproject.databinding.FragmentNumberHelpingBinding
import mnshat.dev.myproject.users.patient.BasePatientFragment


class NumberHelpingFragment : BasePatientFragment<FragmentNumberHelpingBinding>() {

    override fun getLayout() = R.layout.fragment_number_helping


    override fun initializeViews() {
        super.initializeViews()

        binding.recyclerView.adapter = HelpingAdapterAdapter(numbersList())

    }

    override fun setupClickListener() {
        super.setupClickListener()
        binding.iconBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun numbersList() = listOf(
        R.drawable.img_hepling1,
        R.drawable.img_helping2,
        R.drawable.img_helping3,
        R.drawable.img_helping4,
        R.drawable.img_helping5,
        R.drawable.img_helping6,
        R.drawable.img_helping7,
    )
}
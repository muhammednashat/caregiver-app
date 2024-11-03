package mnshat.dev.myproject.commonFeatures.posts

import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentDisplayGratitudeBinding
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.dateTime
import mnshat.dev.myproject.util.getDateAsString
import mnshat.dev.myproject.util.getGratitudeQuestionsList


class DisplayGratitudeFragment  : BasePatientFragment<FragmentDisplayGratitudeBinding>() {

    override fun getLayout() = R.layout.fragment_display_gratitude

    override fun initializeViews() {
        super.initializeViews()
        val gratitude = DisplayGratitudeFragmentArgs.fromBundle(requireArguments()).gratitude
        val questions = getGratitudeQuestionsList(requireActivity())

        binding.question.text = ""
        binding.text.text = gratitude.answer
        binding.date.text = dateTime(gratitude.timeStamp)

    }


}




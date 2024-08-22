package mnshat.dev.myproject.users.patient.main.tools.gratitude

import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentGratitudeBinding
import mnshat.dev.myproject.interfaces.ItemSuggestedGratitudeQuestionsClicked
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.getGratitudeQuestionsList
import mnshat.dev.myproject.util.log


class GratitudeFragment : BasePatientFragment<FragmentGratitudeBinding>(),
    ItemSuggestedGratitudeQuestionsClicked {

    override fun getLayout() = R.layout.fragment_gratitude

    private lateinit var currentQuestion: String
    override fun initializeViews() {
        intiBackgroundBasedOnLang()
        setText(getRandomQuestion())
    }

    private fun setText(text: String) {
        currentQuestion = text
        binding.tvQuestion.text = text
    }


    private fun getRandomQuestion(): String {
        val questions = getGratitudeQuestionsList()
        return questions.random()
    }


    private fun intiBackgroundBasedOnLang() {
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
    }

    override fun setupClickListener() {
        super.setupClickListener()

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnRecommend.setOnClickListener {
            SuggestedGratitudeQuestionsFragment().show(
                childFragmentManager,
                SuggestedGratitudeQuestionsFragment::class.java.name
            )
        }
    }

    override fun onItemClicked(text: String) {
        log(text)
        setText(text)
    }

}
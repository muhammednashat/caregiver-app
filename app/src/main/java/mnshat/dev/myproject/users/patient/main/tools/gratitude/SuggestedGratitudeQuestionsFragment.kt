package mnshat.dev.myproject.users.patient.main.tools.gratitude

import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.SuggestedGratitudeQuestionsAdapter
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentSuggestedGratitudeQuestionsBinding
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.getGratitudeQuestionsList


class SuggestedGratitudeQuestionsFragment : BaseBottomSheetDialogFragment<FragmentSuggestedGratitudeQuestionsBinding>() {

   private lateinit var adapter:SuggestedGratitudeQuestionsAdapter


    override fun getLayout() = R.layout.fragment_suggested_gratitude_questions

    override fun setupClickListener() {
        binding.close.setOnClickListener {
            dismiss()
        }
    }

    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }

        setUpRecyclerView(getGratitudeQuestionsList())

    }

    private fun setUpRecyclerView(gratitudeQuestionsList: List<String>) {
//        adapter  = SuggestedGratitudeQuestionsAdapter(gratitudeQuestionsList,)
    }
}
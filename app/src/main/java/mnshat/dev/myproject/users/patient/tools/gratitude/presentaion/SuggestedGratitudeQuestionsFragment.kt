package mnshat.dev.myproject.users.patient.tools.gratitude.presentaion

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.SuggestedGratitudeQuestionsAdapter
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment2
import mnshat.dev.myproject.databinding.FragmentSuggestedGratitudeQuestionsBinding
import mnshat.dev.myproject.interfaces.OnConfirmButtonClicked
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.getGratitudeQuestionsList


@AndroidEntryPoint
class SuggestedGratitudeQuestionsFragment (private val onConfirmButtonClicked: OnConfirmButtonClicked): BaseBottomSheetDialogFragment2<FragmentSuggestedGratitudeQuestionsBinding>() {

   private lateinit var adapter:SuggestedGratitudeQuestionsAdapter
   private val viewModel: GratitudeViewModel by activityViewModels()

    override fun getLayout() = R.layout.fragment_suggested_gratitude_questions

    override fun setupClickListener() {
        binding.close.setOnClickListener {
            dismiss()
        }
        binding.buttonConfirm.setOnClickListener {
           updateSelectedPosition()
            dismiss()
        }
    }

    private fun updateSelectedPosition() {
        val selectedPosition = adapter.getSelectedPosition()
        viewModel.setSelectedPosition(selectedPosition)
        onConfirmButtonClicked.onConfirmClicked(getGratitudeQuestionsList(requireActivity())[selectedPosition])
    }

    
    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }


    private fun initViewModel() {
        binding.lifecycleOwner = this
        setUpRecyclerView(getGratitudeQuestionsList(requireActivity()),viewModel.getSelectedPosition())
    }

    private fun setUpRecyclerView(gratitudeQuestionsList: List<String>, selectedPosition: Int) {
        adapter  = SuggestedGratitudeQuestionsAdapter(gratitudeQuestionsList,selectedPosition)
        binding.recyclerView.adapter = adapter
    }

}
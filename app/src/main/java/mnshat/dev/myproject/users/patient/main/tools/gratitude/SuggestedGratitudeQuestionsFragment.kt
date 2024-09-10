package mnshat.dev.myproject.users.patient.main.tools.gratitude

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.SuggestedGratitudeQuestionsAdapter
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentSuggestedGratitudeQuestionsBinding
import mnshat.dev.myproject.factories.GratitudeViewModelFactory
import mnshat.dev.myproject.interfaces.OnConfirmButtonClicked
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.getGratitudeQuestionsList


class SuggestedGratitudeQuestionsFragment (private val onConfirmButtonClicked: OnConfirmButtonClicked): BaseBottomSheetDialogFragment<FragmentSuggestedGratitudeQuestionsBinding>() {

   private lateinit var adapter:SuggestedGratitudeQuestionsAdapter
   private lateinit var viewModel:GratitudeViewModel

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
        val factory = GratitudeViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[GratitudeViewModel::class.java]
        binding.lifecycleOwner = this
        setUpRecyclerView(getGratitudeQuestionsList(requireActivity()),viewModel.getSelectedPosition())
    }

    private fun setUpRecyclerView(gratitudeQuestionsList: List<String>, selectedPosition: Int) {
        adapter  = SuggestedGratitudeQuestionsAdapter(gratitudeQuestionsList,selectedPosition)
        binding.recyclerView.adapter = adapter
    }

}
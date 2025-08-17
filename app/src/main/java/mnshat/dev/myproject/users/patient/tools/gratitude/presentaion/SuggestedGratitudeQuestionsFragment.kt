package mnshat.dev.myproject.users.patient.tools.gratitude.presentaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.adapters.SuggestedGratitudeQuestionsAdapter
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentSuggestedGratitudeQuestionsBinding
import mnshat.dev.myproject.interfaces.OnConfirmButtonClicked
import mnshat.dev.myproject.util.getGratitudeQuestionsList


@AndroidEntryPoint
class SuggestedGratitudeQuestionsFragment(
    private val onConfirmButtonClicked: OnConfirmButtonClicked
) : BaseBottomSheetDialogFragment() {

    private lateinit var adapter: SuggestedGratitudeQuestionsAdapter
    private val viewModel: GratitudeViewModel by activityViewModels()
    private lateinit var binding: FragmentSuggestedGratitudeQuestionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuggestedGratitudeQuestionsBinding.inflate(inflater, container, false)
        setUpRecyclerView(
            getGratitudeQuestionsList(requireActivity()), viewModel.getSelectedPosition()
        )
        setListeners()
        return binding.root
    }

    private fun setListeners() {
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
        onConfirmButtonClicked.onConfirmClicked(viewModel.getQuestion(requireActivity(),selectedPosition))
    }

    private fun setUpRecyclerView(gratitudeQuestionsList: List<String>, selectedPosition: Int) {
        adapter = SuggestedGratitudeQuestionsAdapter(gratitudeQuestionsList, selectedPosition)
        binding.recyclerView.adapter = adapter
    }

}
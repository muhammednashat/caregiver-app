package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentSuggestionsBinding
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood
import mnshat.dev.myproject.users.patient.moodTracking.presentaion.adapters.SuggestionsAdapter
import mnshat.dev.myproject.users.patient.moodTracking.presentaion.viewmodels.MoodTrackingViewModel

@AndroidEntryPoint
class SuggestionsFragment : BaseFragment() {

    private lateinit var binding: FragmentSuggestionsBinding
    private val viewModel: MoodTrackingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuggestionsBinding.inflate(inflater, container, false)
        setUpUi(viewModel.getEmoji()!!)
        setUpListeners()
        return binding.root
    }

    private fun setUpListeners() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnNext.setOnClickListener {
          viewModel.updateCurrentDayPostMood()
         findNavController().navigate(R.id.action_suggestionsFragment_to_compareResultsFragment)
        }
    }

    private fun setUpUi(emoji: EmojiMood) {
        setUpSuggestionsRecyclerView(emoji)
        binding.icon1.setImageResource(emoji.emoji)
        binding.icon1.alpha = 1.0f
        binding.title.text = emoji.title
        binding.subTitle.text = emoji.subTitle
        binding.title.setTextColor(Color.parseColor(emoji.buttonColor))
        binding.suggestion.text = emoji.titleSuggestion
        binding.root.visibility = View.VISIBLE
        binding.dynamicContainer.backgroundTintList  = ColorStateList.valueOf(resources.getColor(R. color. white))
        binding.container.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.buttonColor))
        binding.root.setBackgroundColor(Color.parseColor(emoji.backgroundColor))
        binding.btnNext.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.buttonColor))

    }

    private fun setUpSuggestionsRecyclerView(emoji: EmojiMood) {
        val adapter = SuggestionsAdapter(emoji)
        binding.recyclerView.adapter = adapter
    }
}
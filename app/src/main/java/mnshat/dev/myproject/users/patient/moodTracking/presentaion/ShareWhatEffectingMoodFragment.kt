package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentShareWhatEffectingMoodBinding
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EffectingMood
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood


class ShareWhatEffectingMoodFragment : BaseFragment() {

    private lateinit var binding: FragmentShareWhatEffectingMoodBinding
    private val viewModel: MoodViewModel by viewModels()
    private lateinit var effectingAdapter: EffectingMoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShareWhatEffectingMoodBinding.inflate(inflater, container, false)
        updateUiColor(viewModel.getEmoji()!!)
        setUpRecyclerViewEffectingMood(viewModel.getEffectingMood(requireActivity()))
        setUpListener()
        return binding.root
    }

    private fun setUpListener() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpRecyclerViewEffectingMood(list: List<EffectingMood>) {
        effectingAdapter = EffectingMoodAdapter(list)
        val layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.recyclerViewEffectingMood.layoutManager = layoutManager
        binding.recyclerViewEffectingMood.adapter = effectingAdapter
    }
    private fun updateUiColor(emoji: EmojiMood) {
        binding.moodText.text = emoji.name
        binding.moodText.alpha = 1.0f
        binding.icon.setImageResource(emoji.emoji)
        binding.btnNext.setBackgroundResource(R.drawable.gradient_orange)
        binding.root.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.backgroundColor))
        binding.btnNext.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.buttonColor))
    }

}
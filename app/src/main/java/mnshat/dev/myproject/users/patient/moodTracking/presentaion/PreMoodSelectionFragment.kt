package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentPreMoodSelectionBinding
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EffectingMood
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood

@AndroidEntryPoint
class PreMoodSelectionFragment : BaseBottomSheetDialogFragment() ,OnEmojiClickListener  {

    private val viewModel: MoodViewModel by viewModels()
    private  lateinit var adapter: EmojisAdapter
    private  lateinit var effectingAdapter: EffectingMoodAdapter
    private lateinit var binding: FragmentPreMoodSelectionBinding
    private var counterClickingButton = 0
    private var canClickingButton = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPreMoodSelectionBinding.inflate(inflater,container, false)
        setUpListener()
        setUprecyclerView(viewModel.getEmojisStatus(requireActivity()))
        return  binding.root

    }

    private fun setUpListener() {
        binding.backBtn.setOnClickListener {
            dismiss()
        }

      binding.btnNext.setOnClickListener {
          if (canClickingButton){
              counterClickingButton++
              if (counterClickingButton == 1){
                  binding.headerText.text =
                      getString(R.string.would_you_like_to_share_what_s_affecting_your_mood)
                      setUpRecyclerViewEffectingMood(viewModel.getEffectingMood(requireActivity()))
              }else if (counterClickingButton == 2){

              }else{

              }
          }
    }
    }

    private fun setUpRecyclerViewEffectingMood(list:List<EffectingMood>) {
        effectingAdapter = EffectingMoodAdapter(list)
        val layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.recyclerViewEffectingMood.layoutManager = layoutManager
        binding.recyclerViewEffectingMood.adapter = effectingAdapter
        binding.container.visibility = View.VISIBLE
        binding.recyclerView.alpha = 0.0f
    }

    private fun setUprecyclerView(emojisStatus: List<EmojiMood>) {
        adapter = EmojisAdapter(emojisStatus,this)
        val layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
}

    override fun onEmojiClicked(emoji: EmojiMood) {
        updateUiColor(emoji)
    }

    private fun updateUiColor(emoji: EmojiMood) {
        canClickingButton = true
        binding.moodText.text = emoji.title
        binding.moodText.alpha = 1.0f
        binding.icon.setImageResource(emoji.emoji)
        binding.btnNext.setBackgroundResource(R.drawable.gradient_orange)
        binding.root.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.buttonColor))
        binding.btnNext.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.buttonColor))
    }
}
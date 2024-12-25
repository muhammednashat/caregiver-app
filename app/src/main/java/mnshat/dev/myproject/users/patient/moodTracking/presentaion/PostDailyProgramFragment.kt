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
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentPostDailyProgramBinding
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood

@AndroidEntryPoint
class PostDailyProgramFragment : BaseFragment(),OnEmojiClickListener  {

    private val viewModel: MoodViewModel by viewModels()
    private  lateinit var adapter: EmojisAdapter
    private var counterClickingButton = 0
    private var canClickingButton = false
    private lateinit var emoji: EmojiMood


private lateinit var binding:FragmentPostDailyProgramBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostDailyProgramBinding.inflate(inflater,container,false)
        setUpListener()
        setUpMoodsRecyclerView(viewModel.getEmojisStatus(requireActivity()))
        return binding.root
    }

    private fun setUpListener() {

        binding.btnNext.setOnClickListener {
            if (canClickingButton){
                if (counterClickingButton == 0){
                    counterClickingButton++
                    updateUi(emoji)
                }else if (counterClickingButton == 1){

                }
            }
        }
    }

    private fun updateUi(emoji:EmojiMood) {
        setUpSuggestionsRecyclerView(emoji)
        binding.btnNext.text = getString(R.string.okay)
        binding.icon1.setImageResource(emoji.emoji)
        binding.icon1.alpha = 1.0f
        binding.container2.title.text = emoji.title
        binding.container2.subTitle.text = emoji.subTitle
        binding.container2.title.setTextColor(Color.parseColor(emoji.buttonColor))
        binding.container2.suggestion.text = emoji.titleSuggestion

        binding.container2.root.visibility = View.VISIBLE
        binding.dynamicContainer.backgroundTintList  = ColorStateList.valueOf(resources.getColor(R. color. white))
        binding.container.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.buttonColor))
        binding.root.setBackgroundColor(Color.parseColor(emoji.backgroundColor))
        binding.container1.root.visibility = View.GONE

    }

    private fun setUpSuggestionsRecyclerView(emoji: EmojiMood) {
        val adapter = SuggestionsAdapter(emoji)
        binding.container2.recyclerView.adapter = adapter
    }

    private fun setUpMoodsRecyclerView(emojisStatus: List<EmojiMood>) {
        adapter = EmojisAdapter(emojisStatus,this)
        val layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.container1.recyclerView.layoutManager = layoutManager
        binding.container1.recyclerView.adapter = adapter
    }


    override fun onEmojiClicked(emoji: EmojiMood) {
        this.emoji = emoji
        updateUiColor(emoji)
    }
    private fun updateUiColor(emoji: EmojiMood) {
        canClickingButton = true
        binding.icon1.alpha = 0.0f
        binding.container1.moodText.text = emoji.name
        binding.container1.moodText.alpha = 1.0f
        binding.container1.icon.setImageResource(emoji.emoji)
        binding.dynamicContainer.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.backgroundColor))
        binding.container.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.backgroundColor))
        binding.btnNext.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.buttonColor))
    }

}
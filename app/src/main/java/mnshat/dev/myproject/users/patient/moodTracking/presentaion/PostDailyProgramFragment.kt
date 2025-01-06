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
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentPostDailyProgramBinding
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood

@AndroidEntryPoint
class PostDailyProgramFragment : BaseFragment(),OnEmojiClickListener  {

    private val viewModel: MoodViewModel by viewModels()
    private  lateinit var adapter: EmojisAdapter
    private var canClickingButton = false


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
                findNavController().navigate(R.id.action_postDailyProgramFragment_to_suggestionsFragment)
            }
        }
    }

    private fun setUpMoodsRecyclerView(emojisStatus: List<EmojiMood>) {
        adapter = EmojisAdapter(emojisStatus,this)
        val layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    override fun onEmojiClicked(emoji: EmojiMood,index:Int) {
        viewModel.setEmoji(emoji)
        viewModel.setPostMoodIndex(index)
        updateUiColor(viewModel.getEmoji()!!)
    }

    private fun updateUiColor(emoji: EmojiMood) {
        canClickingButton = true
        binding.icon1.alpha = 0.0f
        binding.moodText.text = emoji.name
        binding.moodText.alpha = 1.0f
        binding.icon.setImageResource(emoji.emoji)
        binding.dynamicContainer.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.backgroundColor))
        binding.container.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.backgroundColor))
        binding.btnNext.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.buttonColor))
    }

}
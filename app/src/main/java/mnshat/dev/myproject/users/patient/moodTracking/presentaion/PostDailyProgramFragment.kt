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
import mnshat.dev.myproject.databinding.FragmentPostDailyProgramBinding
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood

@AndroidEntryPoint
class PostDailyProgramFragment : BaseFragment(),OnEmojiClickListener  {

    private val viewModel: MoodViewModel by viewModels()
    private  lateinit var adapter: EmojisAdapter
    private var counterClickingButton = 0
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
                if (counterClickingButton == 0){
                    counterClickingButton++
//                    binding.headerText.text =
//                        getString(R.string.would_you_like_to_share_what_s_affecting_your_mood)
//                    setUpRecyclerViewEffectingMood(viewModel.getEffectingMood(requireActivity()))
                }else if (counterClickingButton == 1){
//                    showStartDailyProgram()
//                  _onNextClickListener.onNextClicked()
//                 dismiss()
                }
            }
        }
    }
    private fun setUpMoodsRecyclerView(emojisStatus: List<EmojiMood>) {
        adapter = EmojisAdapter(emojisStatus,this)
        val layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.container1.recyclerView.layoutManager = layoutManager
        binding.container1.recyclerView.adapter = adapter
    }


    override fun onEmojiClicked(emoji: EmojiMood) {
        updateUiColor(emoji)
    }
    private fun updateUiColor(emoji: EmojiMood) {
        canClickingButton = true
        binding.icon1.visibility = View.GONE
        binding.container1.moodText.text = emoji.name
        binding.container1.moodText.alpha = 1.0f
        binding.container1.icon.setImageResource(emoji.emoji)
//        binding.btnNext.setBackgroundResource(R.drawable.gradient_orange)
        binding.dynamicContainer.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.backgroundColor))
        binding.container.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.backgroundColor))
        binding.btnNext.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.buttonColor))
    }

}
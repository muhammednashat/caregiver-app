package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.DialogStartProgramBinding
import mnshat.dev.myproject.databinding.FragmentPreMoodSelectionBinding
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EffectingMood
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood

@AndroidEntryPoint
class PreMoodSelectionFragment : BaseFragment(),OnEmojiClickListener  {

    private val viewModel: MoodViewModel by viewModels()
    private  lateinit var adapter: EmojisAdapter
    private  lateinit var effectingAdapter: EffectingMoodAdapter
    private lateinit var binding: FragmentPreMoodSelectionBinding
//    private lateinit var _onNextClickListener: OnNextClickListener

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

      binding.btnNext.setOnClickListener {
          if (canClickingButton){
              if (counterClickingButton == 0){
                  counterClickingButton++
                  binding.headerText.text =
                      getString(R.string.would_you_like_to_share_what_s_affecting_your_mood)
                      setUpRecyclerViewEffectingMood(viewModel.getEffectingMood(requireActivity()))
              }else if (counterClickingButton == 1){
                  showStartDailyProgram()
//                  _onNextClickListener.onNextClicked()
//                 dismiss()
              }
          }
    }
    }
    private fun setUprecyclerView(emojisStatus: List<EmojiMood>) {
        adapter = EmojisAdapter(emojisStatus,this)
        val layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }


    private fun setUpRecyclerViewEffectingMood(list:List<EffectingMood>) {
        effectingAdapter = EffectingMoodAdapter(list)
        val layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.recyclerViewEffectingMood.layoutManager = layoutManager
        binding.recyclerViewEffectingMood.adapter = effectingAdapter
        binding.container.visibility = View.VISIBLE
        binding.recyclerView.alpha = 0.0f
    }


    override fun onEmojiClicked(emoji: EmojiMood) {
        updateUiColor(emoji)
    }

    private fun updateUiColor(emoji: EmojiMood) {
        canClickingButton = true
        binding.moodText.text = emoji.name
        binding.moodText.alpha = 1.0f
        binding.icon.setImageResource(emoji.emoji)
        binding.btnNext.setBackgroundResource(R.drawable.gradient_orange)
        binding.root.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.backgroundColor))
        binding.btnNext.backgroundTintList  = ColorStateList.valueOf(Color.parseColor(emoji.buttonColor))
    }
    fun setOnNextClickListener(onNextClickListener: OnNextClickListener){
    }
    private fun showStartDailyProgram() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogStartProgramBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false)

        val window = dialog.window
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.8).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }

        dialogBinding.icClose.setOnClickListener {
        }
        dialogBinding.button.setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(R.id.action_preMoodSelectionFragment_to_educationalFragment)
        }
        dialog.show()
    }

}



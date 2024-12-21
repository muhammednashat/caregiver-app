package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.databinding.FragmentPreMoodSelectionBinding
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood

@AndroidEntryPoint
class PreMoodSelectionFragment : BaseFragment(),OnEmojiClickListener  {

    private val viewModel: MoodViewModel by viewModels()
    private  lateinit var adapter: EmojisAdapter
    private lateinit var binding: FragmentPreMoodSelectionBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPreMoodSelectionBinding.inflate(inflater,container, false)
        setUpRecyclerView(viewModel.getEmojisStatus(requireActivity()))
        return  binding.root

    }

    private fun setUpRecyclerView(emojisStatus: List<EmojiMood>) {
        adapter = EmojisAdapter(emojisStatus,this)
        val layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
}

    override fun onEmojiClicked(emoji: EmojiMood) {
        updateUiColor(emoji)
    }

    private fun updateUiColor(emoji: EmojiMood) {
//       binding.button2.setBackgroundColor(emoji.buttonColor)
    }
}
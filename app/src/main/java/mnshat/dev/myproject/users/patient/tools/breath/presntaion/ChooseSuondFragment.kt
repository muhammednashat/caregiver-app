package mnshat.dev.myproject.users.patient.tools.breath.presntaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.Sound
import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.getSoundsList
import mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion.OnItemSoundClicked
import mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion.SoundsAdapter
import mnshat.dev.myproject.databinding.FragmentChooseSuondBinding

class ChooseSuondFragment : BaseBottomSheetDialogFragment(), OnItemSoundClicked {

    private lateinit var binding: FragmentChooseSuondBinding
    private lateinit var viewModel: BreathViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseSuondBinding.inflate(inflater, container, false)
        initViewModel()
        setRecycler(getSoundsList(requireActivity()))
        return binding.root
    }

    private fun setRecycler(sounds: List<Sound>) {

        binding.recycler.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = SoundsAdapter(
                sounds,
                requireActivity(),
                this@ChooseSuondFragment
            )
        }
    }

    private fun initViewModel() {

    }

    override fun onItemClicked(soundId: Int?) {
    viewModel.updateSound(soundId)
        dismiss()
    }
}
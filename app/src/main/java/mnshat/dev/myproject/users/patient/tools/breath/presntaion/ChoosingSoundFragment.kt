package mnshat.dev.myproject.users.patient.tools.breath.presntaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.users.patient.tools.breath.model.Sound
import mnshat.dev.myproject.users.patient.tools.breath.model.getSoundsList
import mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion.OnItemSoundClicked
import mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion.SoundsAdapter
import mnshat.dev.myproject.databinding.FragmentChooseSuondBinding
import kotlin.getValue

@AndroidEntryPoint

class ChoosingSoundFragment : BaseFragment()  , OnItemSoundClicked{

    private  val viewModel : BreathViewModel by activityViewModels()
    private  lateinit var  binding : FragmentChooseSuondBinding
    private lateinit var soundsAdapter: SoundsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseSuondBinding.inflate(inflater, container, false)

        setSoundsRecycler(viewModel.listOfSounds(requireActivity()))

        setUpListeners()

        return  binding.root

    }


    private fun setUpListeners() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_choosingSoundFragment_to_mainBreathFragment)
        }
    }
    private fun setSoundsRecycler(sounds: List<Sound>) {
        soundsAdapter = SoundsAdapter(sounds, requireActivity(), this)
        binding.recycler.adapter = soundsAdapter
        binding.recycler.layoutManager = GridLayoutManager(context, 2)
    }

    override fun onItemClicked(soundId: Int?) {
       binding.button.isEnabled = true
        viewModel.soundId = soundId!!
    }
}
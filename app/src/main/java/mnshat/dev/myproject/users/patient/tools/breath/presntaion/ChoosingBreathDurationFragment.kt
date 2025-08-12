package mnshat.dev.myproject.users.patient.tools.breath.presntaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.Sound
import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.getSoundsList
import mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion.OnItemSoundClicked
import mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion.SoundsAdapter
import mnshat.dev.myproject.databinding.FragmentChoosingBreathDurationBinding
import mnshat.dev.myproject.users.patient.tools.breath.data.Duration
import mnshat.dev.myproject.users.patient.tools.breath.presntaion.ChooseSuondFragment
import kotlin.getValue

@AndroidEntryPoint

class ChoosingBreathDurationFragment : Fragment(), MinutesListener , OnItemSoundClicked {

    private  val viewModel : BreathViewModel by activityViewModels()
    private  lateinit var  binding : FragmentChoosingBreathDurationBinding
    private lateinit var minutesAdapter: MinutesAdapter
    private lateinit var soundsAdapter: SoundsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChoosingBreathDurationBinding.inflate(inflater, container, false)
        init()
        setUpListeners()
        return  binding.root

    }

    private fun setUpListeners() {
        binding.button.setOnClickListener {
            binding.imageView.visibility = View.GONE
            binding.text.text = "dfadf"
            binding.button.text = "ok"
            setSoundsRecycler(getSoundsList(requireActivity()))
        }
    }
    private fun init() {
     val list = viewModel.listOfDurations(requireActivity())

        minutesAdapter = MinutesAdapter(list, this)
        binding.recycler.adapter = minutesAdapter
        binding.recycler.layoutManager = GridLayoutManager(
            requireActivity(),
            3,
            GridLayoutManager.VERTICAL,
            false
        )


}

    private fun setSoundsRecycler(sounds: List<Sound>) {

        binding.recycler.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = SoundsAdapter(
                sounds,
                requireActivity(),
                this@ChoosingBreathDurationFragment
            )
        }
    }

    override fun onItemClicked(duration: Duration) {
        binding.imageView.setImageResource(duration.image)
    }

    override fun onItemClicked(soundId: Int?) {

    }
}
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
import mnshat.dev.myproject.databinding.FragmentChoosingBreathDurationBinding
import mnshat.dev.myproject.users.patient.tools.breath.data.Duration
import kotlin.getValue

@AndroidEntryPoint

class ChoosingBreathDurationFragment : BaseFragment(), MinutesListener  {

    private  val viewModel : BreathViewModel by activityViewModels()
    private  lateinit var  binding : FragmentChoosingBreathDurationBinding
    private lateinit var minutesAdapter: MinutesAdapter

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

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.button.setOnClickListener {

            findNavController().navigate(R.id.action_choosingBreathDurationFragment_to_choosingSoundFragment)

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


    override fun onItemClicked(duration: Duration) {
        binding.imageView.setImageResource(duration.image)
        binding.button.isEnabled = true
        viewModel.currentDuration = duration.durationNumber

    }


}
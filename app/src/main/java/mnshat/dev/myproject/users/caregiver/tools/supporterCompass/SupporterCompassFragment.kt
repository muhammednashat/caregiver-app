package mnshat.dev.myproject.users.caregiver.tools.supporterCompass

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.StepsAdapter
import mnshat.dev.myproject.databinding.FragmentSupporterCompassBinding
import mnshat.dev.myproject.factories.CaregiverToolsViewModelFactory
import mnshat.dev.myproject.interfaces.ItemStepsClicked
import mnshat.dev.myproject.model.Step
import mnshat.dev.myproject.users.caregiver.main.BaseCaregiverFragment
import mnshat.dev.myproject.users.caregiver.tools.CaregiverToolsViewModel
import mnshat.dev.myproject.util.data.stepsBuildStrengthList
import mnshat.dev.myproject.util.data.stepsCompassList

class SupporterCompassFragment : BaseCaregiverFragment<FragmentSupporterCompassBinding>(),ItemStepsClicked {

    private lateinit var viewModel: CaregiverToolsViewModel
    private lateinit var stepsAdapter: StepsAdapter

    override fun getLayout()= R.layout.fragment_supporter_compass


    override fun setupClickListener() {
        super.setupClickListener()
        binding.iconBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val factory =
            CaregiverToolsViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[CaregiverToolsViewModel::class.java]
        updateUI(stepsCompassList(requireActivity()))

    }

    private fun updateUI(steps: List<Step>) {
        stepsAdapter = StepsAdapter(steps,this)
        binding.recyclerView.adapter = stepsAdapter
    }

    override fun onItemClicked(index: Int) {
        viewModel.setCurrentIndex(index)
        viewModel.setCurrentList(stepsCompassList(requireActivity()))
       findNavController().navigate(R.id.action_supporterCompassFragment_to_stepsFragment)
     }

}
package mnshat.dev.myproject.users.caregiver.tools.strengthBuildingPlan

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.StepsAdapter
import mnshat.dev.myproject.databinding.FragmentStrengthBuildingPlanBinding
import mnshat.dev.myproject.factories.CaregiverToolsViewModelFactory
import mnshat.dev.myproject.interfaces.ItemStepsClicked
import mnshat.dev.myproject.model.Step
import mnshat.dev.myproject.users.caregiver.main.BaseCaregiverFragment
import mnshat.dev.myproject.users.caregiver.tools.CaregiverToolsViewModel
import mnshat.dev.myproject.util.data.stepsBuildStrengthList


class StrengthBuildingPlanFragment : BaseCaregiverFragment<FragmentStrengthBuildingPlanBinding>(),
    ItemStepsClicked {

    private lateinit var viewModel: CaregiverToolsViewModel
    private lateinit var stepsAdapter: StepsAdapter

    override fun getLayout()= R.layout.fragment_strength_building_plan

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
        updateUI(stepsBuildStrengthList(requireActivity()))

    }

    private fun updateUI(steps: List<Step>) {
        stepsAdapter = StepsAdapter(steps,this)
        binding.recyclerView.adapter = stepsAdapter
    }

    override fun onItemClicked(index: Int) {
        viewModel.setCurrentIndex(index)
        viewModel.setCurrentList(stepsBuildStrengthList(requireActivity()))

        findNavController().navigate(R.id.action_strengthBuildingPlanFragment_to_stepsFragment)
    }

}
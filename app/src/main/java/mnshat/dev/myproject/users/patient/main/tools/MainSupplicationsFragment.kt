package mnshat.dev.myproject.users.patient.main.tools

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.SuggestedSupplicationAdapter
import mnshat.dev.myproject.adapters.UserSupplicationAdapter
import mnshat.dev.myproject.databinding.FragmentMainSupplicationsBinding
import mnshat.dev.myproject.factories.ToolsViewModelFactory
import mnshat.dev.myproject.interfaces.ItemSupplicationClicked
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ENGLISH_KEY


class MainSupplicationsFragment : BasePatientFragment<FragmentMainSupplicationsBinding>(),

    ItemSupplicationClicked {

    private lateinit var viewModel: ToolsViewModel

    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.backArrow.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
    }

    override fun setupClickListener() {
        super.setupClickListener()
        binding.fab.setOnClickListener{
            AddSupplicationsFragment().show(childFragmentManager, AddSupplicationsFragment::class.java.name)
        }
        binding.backArrow.setOnClickListener{
           findNavController().popBackStack()
        }
        binding.textShowAll.setOnClickListener{
            val action = MainSupplicationsFragmentDirections
                .actionMainSupplicationsFragmentToUserSupplicationsFragment(viewModel.getUserSupplication().toTypedArray())
            findNavController().navigate(action)
        }

    }
    override fun getLayout()= R.layout.fragment_main_supplications

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ToolsViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[ToolsViewModel::class.java]
        binding.lifecycleOwner = this
        observeViewModel()

    }

    private fun setupSuggestedSupplicationRecyclerView(
        suggestedSupplication: List<Supplication>
    ) {
        val adapterSuggestedSupplication = SuggestedSupplicationAdapter(suggestedSupplication,this)
        binding.suggestedRecyclerView.adapter = adapterSuggestedSupplication
    }

    private fun setupUserSupplicationRecyclerView(
        userSupplication: List<Supplication>
    ) {
        val adapterUserSupplication = UserSupplicationAdapter(userSupplication,this)
        binding.userSupplicationRecyclerView.adapter = adapterUserSupplication
    }


    private fun observeViewModel() {

        viewModel.userSupplication.observe(viewLifecycleOwner) { items ->
            items.let {
                setupUserSupplicationRecyclerView(items)
            }
        }
        viewModel.suggestedSupplication.observe(viewLifecycleOwner) { items ->
            items.let {
                setupSuggestedSupplicationRecyclerView(items)
            }
        }
    }

    override fun onItemClicked(supplication: Supplication) {
        val action = MainSupplicationsFragmentDirections
            .actionMainSupplicationsFragmentToSupplicationsFragment(supplication)
        findNavController().navigate(action)
    }

}
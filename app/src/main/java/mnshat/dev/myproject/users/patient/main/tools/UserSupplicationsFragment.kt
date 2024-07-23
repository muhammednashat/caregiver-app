package mnshat.dev.myproject.users.patient.main.tools

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.SuggestedSupplicationAdapter
import mnshat.dev.myproject.adapters.UserSupplicationAdapter
import mnshat.dev.myproject.databinding.FragmentUserSupplicationsBinding
import mnshat.dev.myproject.factories.ToolsViewModelFactory
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.log


class UserSupplicationsFragment : BasePatientFragment<FragmentUserSupplicationsBinding>() {

    private lateinit var viewModel: ToolsViewModel

    override fun initializeViews() {

    }

    override fun getLayout()= R.layout.fragment_user_supplications

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
        val adapterSuggestedSupplication = SuggestedSupplicationAdapter(suggestedSupplication)
        binding.suggestedRecyclerView.adapter = adapterSuggestedSupplication
    }

    private fun setupUserSupplicationRecyclerView(
        userSupplication: List<Supplication>
    ) {
        val adapterUserSupplication = UserSupplicationAdapter(userSupplication)
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
    
}
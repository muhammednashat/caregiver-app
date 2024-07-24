package mnshat.dev.myproject.users.patient.main.tools

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.UserSupplicationAdapter
import mnshat.dev.myproject.databinding.FragmentUserSupplicationsBinding
import mnshat.dev.myproject.factories.ToolsViewModelFactory
import mnshat.dev.myproject.interfaces.ItemSupplicationClicked
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ENGLISH_KEY


class UserSupplicationsFragment : BasePatientFragment<FragmentUserSupplicationsBinding>(),
    ItemSupplicationClicked {

    private lateinit var viewModel: ToolsViewModel
    private lateinit var listOfSupplications: List<Supplication>

    override fun initializeViews() {

        retrieveDataFromArguments()
        intiBackgroundBasedOnLang()

    }

    private fun intiBackgroundBasedOnLang() {
        if (currentLang != ENGLISH_KEY) {
            binding.backArrow.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
    }

    private fun retrieveDataFromArguments() {
        arguments.let {
            listOfSupplications = it?.getParcelableArrayList<Supplication>("listOfSupplications")!!
            setupUserSupplicationRecyclerView(listOfSupplications)
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


    }
    override fun getLayout()= R.layout.fragment_user_supplications

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ToolsViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[ToolsViewModel::class.java]
        binding.lifecycleOwner = this
        observeViewModel()

    }


    private fun setupUserSupplicationRecyclerView(
        userSupplication: List<Supplication>
    ) {
        val adapterUserSupplication = UserSupplicationAdapter(userSupplication,this)
        binding.userSupplicationRecyclerView.adapter = adapterUserSupplication
    }


    private fun observeViewModel() {

    }

    override fun onItemClicked(supplication: Supplication) {
//        val action = MainSupplicationsFragmentDirections
//            .actionMainSupplicationsFragmentToSupplicationsFragment(supplication)
//        findNavController().navigate(action)
    }

}
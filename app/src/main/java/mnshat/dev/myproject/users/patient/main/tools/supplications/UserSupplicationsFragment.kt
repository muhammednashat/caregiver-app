package mnshat.dev.myproject.users.patient.main.tools.supplications

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.UserSupplicationAdapter
import mnshat.dev.myproject.databinding.FragmentUserSupplicationsBinding
import mnshat.dev.myproject.factories.SupplicationsViewModelFactory
import mnshat.dev.myproject.interfaces.DataReLoader
import mnshat.dev.myproject.interfaces.ItemSupplicationClicked
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ENGLISH_KEY


class UserSupplicationsFragment : BasePatientFragment<FragmentUserSupplicationsBinding>(),
    ItemSupplicationClicked, DataReLoader {

    private lateinit var viewModel: SupplicationsViewModel

    override fun initializeViews() {

        intiBackgroundBasedOnLang()

    }

    private fun intiBackgroundBasedOnLang() {
        if (currentLang != ENGLISH_KEY) {
            binding.backArrow.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
    }

    private fun retrieveDataFromArguments() {

        val args: UserSupplicationsFragmentArgs by navArgs()
        setupUserSupplicationRecyclerView(args.supplications.toList())
    }

    override fun setupClickListener() {
        super.setupClickListener()
        binding.fab.setOnClickListener {
            val addFragment = AddSupplicationsFragment()
            addFragment.setDataReLoader(this)
            addFragment.show(childFragmentManager, AddSupplicationsFragment::class.java.name)
        }
        binding.backArrow.setOnClickListener{
            findNavController().popBackStack()
        }


    }
    override fun getLayout()= R.layout.fragment_user_supplications

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = SupplicationsViewModelFactory(sharedPreferences, activity?.application!!)
        retrieveDataFromArguments()

        viewModel = ViewModelProvider(requireActivity(), factory)[SupplicationsViewModel::class.java]
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
    private fun retrieveSupplicationsData() {
        showProgressDialog()
        viewModel.getUserSupplications {
            setupUserSupplicationRecyclerView(it)
            dismissProgressDialog()
        }
    }
    override fun reloadData() {
        retrieveSupplicationsData()
    }

    override fun onItemClicked(supplication: Supplication) {
//        val action = MainSupplicationsFragmentDirections
//            .actionMainSupplicationsFragmentToSupplicationsFragment(supplication)
//        findNavController().navigate(action)
    }

}
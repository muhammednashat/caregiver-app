package mnshat.dev.myproject.users.patient.main.tools.supplications

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.SuggestedSupplicationAdapter
import mnshat.dev.myproject.adapters.UserSupplicationAdapter
import mnshat.dev.myproject.databinding.FragmentMainSupplicationsBinding
import mnshat.dev.myproject.factories.SupplicationsViewModelFactory
import mnshat.dev.myproject.interfaces.DataReLoader
import mnshat.dev.myproject.interfaces.ItemSupplicationClicked
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ENGLISH_KEY


class MainSupplicationsFragment : BasePatientFragment<FragmentMainSupplicationsBinding>(),

    ItemSupplicationClicked,DataReLoader {

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
        binding.textShowAll.setOnClickListener{
            navigateToUserSupplications()
        }

    }

    private fun navigateToUserSupplications() {
        val action = MainSupplicationsFragmentDirections
            .actionMainSupplicationsFragmentToUserSupplicationsFragment(viewModel.userSupplications.value?.toTypedArray()!!)
        findNavController().navigate(action)
    }

    override fun getLayout()= R.layout.fragment_main_supplications

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = SupplicationsViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[SupplicationsViewModel::class.java]
        binding.lifecycleOwner = this
        retrieveSupplicationsData()
        observeViewModel()

    }

    private fun retrieveSupplicationsData() {
        showProgressDialog()
        viewModel.getSuggestedSupplications { items ->

            setupSuggestedSupplicationRecyclerView(items)

        }
        viewModel.getUserSupplications {


            setupUserSupplicationRecyclerView(it)


        }
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


        if(userSupplication.isNotEmpty()){

            val adapterUserSupplication = UserSupplicationAdapter(userSupplication,this)
            binding.userSupplicationRecyclerView.adapter = adapterUserSupplication
            binding.textNoItems.visibility = View.GONE
            binding.textShowAll.visibility = View.VISIBLE
            binding.userSupplicationRecyclerView.visibility = View.VISIBLE

        }else{
            binding.userSupplicationRecyclerView.visibility = View.GONE
            binding.textShowAll.visibility = View.GONE
            binding.textNoItems.visibility = View.VISIBLE
        }

    }

    private fun observeViewModel() {

        viewModel.isDismissProgressDialog.observe(viewLifecycleOwner) { isDismissProgressDialog ->
            if (isDismissProgressDialog) {
                dismissProgressDialog()
                binding.constraintData.visibility = View.VISIBLE
                viewModel.resetIsDismissProgressDialog()
            }
        }
    }

    override fun onItemClicked(view:View,supplication: Supplication) {
        val action = MainSupplicationsFragmentDirections
            .actionMainSupplicationsFragmentToSupplicationsFragment(supplication)
        findNavController().navigate(action)
    }

    override fun reloadData() {
        retrieveSupplicationsData()
    }

}
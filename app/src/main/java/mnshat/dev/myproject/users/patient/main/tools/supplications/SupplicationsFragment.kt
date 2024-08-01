package mnshat.dev.myproject.users.patient.main.tools.supplications

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentSupplicationsBinding
import mnshat.dev.myproject.factories.SupplicationsViewModelFactory
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.log


class SupplicationsFragment : BasePatientFragment<FragmentSupplicationsBinding>() {

    private lateinit var supplication: Supplication
    private lateinit var viewModel: SupplicationsViewModel


    override fun initializeViews() {
    }

    private fun retrieveDataFromArguments() {

        val args: SupplicationsFragmentArgs by navArgs()
        viewModel.setSupplication(args.supplication)
    }

    override fun getLayout()= R.layout.fragment_supplications
    private fun observeViewModel() {
        viewModel.supplication.observe(viewLifecycleOwner) {
            log(it.name!!)

        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        retrieveDataFromArguments()
        observeViewModel()

    }

    private fun initViewModel() {
        val factory = SupplicationsViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel =
            ViewModelProvider(requireActivity(), factory)[SupplicationsViewModel::class.java]
        binding.lifecycleOwner = this
    }

    fun showFulltext(){

    }
}
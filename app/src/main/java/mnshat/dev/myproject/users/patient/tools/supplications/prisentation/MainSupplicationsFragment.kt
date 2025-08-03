package mnshat.dev.myproject.users.patient.tools.supplications.prisentation

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.SuggestedSupplicationAdapter
import mnshat.dev.myproject.adapters.UserSupplicationAdapter
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentAddAzcarBinding
import mnshat.dev.myproject.databinding.FragmentAddSupporterBinding
import mnshat.dev.myproject.databinding.FragmentMainSupplicationsBinding
import mnshat.dev.myproject.databinding.FragmentSupplicationsIntroBinding
import mnshat.dev.myproject.interfaces.DataReLoader
import mnshat.dev.myproject.interfaces.ItemSupplicationClicked
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.users.patient.BasePatientFragment
import mnshat.dev.myproject.users.patient.supporters.presentation.SupporterViewModel
import mnshat.dev.myproject.util.ENGLISH_KEY
import kotlin.getValue

@AndroidEntryPoint
class MainSupplicationsFragment : BaseFragment(),

    ItemSupplicationClicked,DataReLoader {

   private val viewModel: SupplicationViewModel by viewModels()

    private lateinit var binding: FragmentMainSupplicationsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainSupplicationsBinding.inflate(inflater, container, false)
        checkInternetConnection()
        return binding.root
    }
    private fun checkInternetConnection() {
        if (isConnected()) {
            retrieveSupplicationsData()
            observeViewModel()
            setupClickListener()
        } else {
            showNoInternetDialog()
        }
    }
    private fun showNoInternetDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.no_internet_connection))
            .setMessage(getString(R.string.please_check_your_internet_connection_and_try_again))
            .setPositiveButton(getString(R.string.try_again)) { dialog, _ ->
                checkInternetConnection()
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

     fun setupClickListener() {
        binding.fab.setOnClickListener {
            AddSupplicationDialog().show(parentFragmentManager, "AddSupplicationDialog")
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
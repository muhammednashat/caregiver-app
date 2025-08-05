package mnshat.dev.myproject.users.patient.tools.supplications.prisentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.SuggestedSupplicationAdapter
import mnshat.dev.myproject.adapters.UserSupplicationAdapter
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentMainSupplicationsBinding
import mnshat.dev.myproject.interfaces.ItemSupplicationClicked
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.util.log
import kotlin.getValue

@AndroidEntryPoint
class MainSupplicationsFragment : BaseFragment(),
    ItemSupplicationClicked {

   private val viewModel: SupplicationViewModel by activityViewModels()

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
            showProgressDialog()
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
            findNavController().navigate(R.id.action_mainSupplicationsFragment_to_userSupplicationsFragment)
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
            binding.showUserSupplications.visibility = View.VISIBLE

        }else{
            binding.showUserSupplications.visibility = View.GONE
            binding.textNoItems.visibility = View.VISIBLE
        }

    }

    private fun observeViewModel() {
        viewModel.userSupplications.observe(viewLifecycleOwner) {
            log("userSupplications ${it.size}")
            dismissProgressDialog()
            binding.constraintData.visibility = View.VISIBLE
            setupUserSupplicationRecyclerView(it)
        }
        viewModel.suggestedSupplication.observe(viewLifecycleOwner) {
            log("suggestedSupplication ${it.size}")
            dismissProgressDialog()
            binding.constraintData.visibility = View.VISIBLE
            binding.suggestedSupplications.visibility = View.VISIBLE
            setupSuggestedSupplicationRecyclerView(it)
        }

    }

    override fun onItemClicked(view:View,supplication: Supplication) {
        viewModel.selectedSupplication = supplication
        findNavController().navigate(R.id.action_mainSupplicationsFragment_to_supplicationsFragment)
    }






}
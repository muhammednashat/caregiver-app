package mnshat.dev.myproject.users.patient.tools.gratitude.presentaion

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.GratitudeAdapter
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentGratitudeListBinding
import mnshat.dev.myproject.util.getGratitudeQuestionsList
import mnshat.dev.myproject.util.log
import kotlin.getValue

class GratitudeListFragment  : BaseFragment() {

    private lateinit var binding: FragmentGratitudeListBinding
    private val viewModel: GratitudeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGratitudeListBinding.inflate(inflater, container, false)
        checkInternetConnection()

        return binding.root
    }
    private fun checkInternetConnection() {
        if (isConnected()) {
            initializeViews()
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
    private   fun setupClickListener() {
        binding.btnAddGratitude.setOnClickListener {
         findNavController().navigate(R.id.action_gratitudeListFragment_to_gratitudeFragment)
        }
        binding.icBack.setOnClickListener {
         findNavController().popBackStack()
        }
    }



    fun initializeViews() {
        showProgressDialog()
        viewModel.retrieveGratitudeListRemotely()
    }

    private  fun  observeViewModel(){
        viewModel.gratitudeList.observe(viewLifecycleOwner){
            log(it.toString() + "fsadfdsfad")
            if (it.isNotEmpty()){
                val adapter = GratitudeAdapter(it, getGratitudeQuestionsList(requireActivity()))
                binding.recyclerViewGratitude.adapter = adapter
            }else{
                showNoItemsView()
            }
            dismissProgressDialog()
        }
    }

    private fun showNoItemsView() {
        binding.gratitudeList.visibility = View.GONE
        binding.noItems.visibility = View.VISIBLE
    }
}
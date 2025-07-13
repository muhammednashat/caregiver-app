package mnshat.dev.myproject.users.patient.supporters.presentation

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
import mnshat.dev.myproject.adapters.SupporterListener
import mnshat.dev.myproject.adapters.SupportersAdapter
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.DialogAddSupporterBinding
import mnshat.dev.myproject.databinding.DialogCannotAddSupporterBinding
import mnshat.dev.myproject.databinding.FragmentSupportersBinding
import mnshat.dev.myproject.util.log

@AndroidEntryPoint
class SupportersFragment : BaseFragment() {

    private lateinit var binding: FragmentSupportersBinding

    private lateinit var adapter: SupportersAdapter
    private val viewModel: SupporterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSupportersBinding.inflate(inflater, container, false)
        checkConnection()
        initAdapter()
        setupClickListener()
        observeViewModel()
        return binding.root

    }
    private fun checkConnection() {
        if(isConnected()){
            initializeViews()
        }else{
            binding.noInternet.layout.visibility = View.VISIBLE
        }
    }

    private fun observeViewModel() {
        viewModel.supportersProfile.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it)
                showRecycler(true)
            } else {
                showRecycler(false)
            log(" observeViewModel supportersProfile =>  $it")
        }
    }
    }

    private fun retry(){
        if(isConnected()){
            binding.noInternet.layout.visibility = View.GONE

            initializeViews()
        }else{
            binding.noInternet.layout.visibility = View.VISIBLE
        }
    }



    private fun initializeViews() {
        if (viewModel.userProfile().hasPartner!!) {
            log("has partner")
            showProgressDialog()
            viewModel.retrieveSupporters()
        }else{
            log("no partner")
            showRecycler(false)
        }


    }

    private fun initAdapter() {
        adapter = SupportersAdapter(requireActivity(), SupporterListener {
            val action = SupportersFragmentDirections.actionSupportesFragmentToSupporterDetailsFragment(it)
            findNavController().navigate(action)
        })
        binding.recyclerSupporters.adapter = adapter
    }



    private fun showRecycler(boolean: Boolean) {
        dismissProgressDialog()

        if (boolean) {
            binding.noSupporters.visibility = View.GONE
            binding.supporters.visibility = View.VISIBLE
        } else {
            binding.supporters.visibility = View.GONE
            binding.noSupporters.visibility = View.VISIBLE
        }

    }


  private  fun setupClickListener() {

        binding.btAddSupporter.setOnClickListener {
            showDialogAdding()
        }
        binding.icAdd.setOnClickListener {
            if (viewModel.userProfile().supportersNumber!! >= 3) {
                showDialogCannotAdding()
            }else{
                showDialogAdding()
            }
        }
        binding.icBack.setOnClickListener {
            activity?.finish()
        }
      binding.noInternet.btRetry.setOnClickListener {
          retry()
      }
    }

    private fun showDialogAdding() {
        sharedDialog = Dialog(requireContext())
        sharedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogAddSupporterBinding.inflate(layoutInflater)
        sharedDialog.setContentView(dialogBinding.root)
        sharedDialog.setCanceledOnTouchOutside(true)
        if (viewModel.userProfile().supportersNumber!! >= 3) {
            dialogBinding.icClose.visibility = View.GONE
        }
        val window = sharedDialog.window
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        sharedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBinding.btAddNew.setOnClickListener {
            sharedDialog.dismiss()
            findNavController().navigate(R.id.action_supportesFragment_to_addSupporterFragment)
        }
        dialogBinding.icClose.setOnClickListener {
            sharedDialog.dismiss()
        }

        sharedDialog.show()
    }

    private fun showDialogCannotAdding() {
        sharedDialog = Dialog(requireContext())
        sharedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogCannotAddSupporterBinding.inflate(layoutInflater)
        sharedDialog.setContentView(dialogBinding.root)
        sharedDialog.setCanceledOnTouchOutside(true)
        val window = sharedDialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        sharedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBinding.btOk.setOnClickListener {
            sharedDialog.dismiss()
        }
        dialogBinding.icClose.setOnClickListener {
            sharedDialog.dismiss()
        }
        sharedDialog.show()
    }



}
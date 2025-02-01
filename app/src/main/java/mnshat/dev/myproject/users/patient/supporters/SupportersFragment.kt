package mnshat.dev.myproject.users.patient.supporters

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.SupporterListener
import mnshat.dev.myproject.adapters.SupportersAdapter
import mnshat.dev.myproject.databinding.DialogAddSupporterBinding
import mnshat.dev.myproject.databinding.DialogCannotAddSupporterBinding
import mnshat.dev.myproject.databinding.FragmentSupportersBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.NUMBER_SUPPORTERS
import mnshat.dev.myproject.util.USER_EMAIL
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.log


class SupportersFragment : BaseSupporterFragment<FragmentSupportersBinding>() {
    private lateinit var adapter: SupportersAdapter

    override fun initializeViews() {
        showProgressDialog()

        initAdapter()
        if (sharedPreferences.getBoolean(HAS_PARTNER)){
            retrieveUsers()
        }else{
            showRecycler(false)
        }

        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
    }

    private fun initAdapter() {
        adapter = SupportersAdapter(requireActivity(), SupporterListener {
            val action = SupportersFragmentDirections.actionSupportesFragmentToSupporterDetailsFragment(it)
            findNavController().navigate(action)
        })
        binding.recyclerSupporters.adapter = adapter
    }

    private fun retrieveUsers() {
            FirebaseService.retrieveUser(sharedPreferences.getString(USER_ID)){ user ->
                user?.storeDataLocally(sharedPreferences)
                FirebaseService.retrieveUsers(user?.supports){
                    it?.let {
                        adapter.submitList(it)
                        showRecycler(true)
                    }
                }
            }
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



    override fun getLayout() =
        R.layout.fragment_supporters

    override fun setupClickListener() {

        binding.btAddSupporter.setOnClickListener {
            showDialogAdding()
        }
        binding.icAdd.setOnClickListener {
            if (sharedPreferences.getInt(NUMBER_SUPPORTERS) >= 3) {
                showDialogCannotAdding()
            }else{
                showDialogAdding()
            }
        }
        binding.icBack.setOnClickListener {
            activity?.finish()
        }
    }

    private fun showDialogAdding() {
        sharedDialog = Dialog(requireContext())
        sharedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogAddSupporterBinding.inflate(layoutInflater)
        sharedDialog.setContentView(dialogBinding.root)
        sharedDialog.setCanceledOnTouchOutside(true)
        if (sharedPreferences.getInt(NUMBER_SUPPORTERS) >= 3) {
            dialogBinding.icClose.visibility = View.GONE
        }
        val window = sharedDialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        sharedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBinding.btAddNew.setOnClickListener {
            sharedDialog.dismiss()
            findNavController().navigate(R.id.action_supportesFragment_to_addSupporterFragment)
        }
        dialogBinding.icClose.setOnClickListener {
            sharedDialog.dismiss()
        }
        dialogBinding.btRecovery.setOnClickListener {
            sharedDialog.dismiss()
            findNavController().navigate(R.id.action_supportesFragment_to_suppporterRecoveryFragment)
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
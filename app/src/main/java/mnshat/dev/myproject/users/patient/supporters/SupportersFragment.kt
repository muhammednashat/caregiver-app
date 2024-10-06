package mnshat.dev.myproject.users.patient.supporters

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.DialogAddSupporterBinding
import mnshat.dev.myproject.databinding.DialogCannotAddSupporterBinding
import mnshat.dev.myproject.databinding.FragmentSupportersBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.NUMBER_SUPPORTERS
import mnshat.dev.myproject.util.log


class SupportersFragment : BaseSupporterFragment<FragmentSupportersBinding>() {
    private lateinit var adapter: SupportersAdapter

    override fun initializeViews() {
        showProgressDialog()

        adapter = SupportersAdapter(requireActivity(),SupporterListener {

            val action = SupportersFragmentDirections.actionSupportesFragmentToSupporterDetailsFragment(it)
            findNavController().navigate(action)

        })
         binding.recyclerSupporters.adapter = adapter
        FirebaseService.listenForUserDataChanges {
            it?.let {
                it.storeDataLocally(sharedPreferences)

                if (sharedPreferences.getBoolean(HAS_PARTNER)){

                    FirebaseService.retrieveUsersByEmails(it.supports){
                        it?.let {
                            log("${it.toString()} ")
                 adapter.submitList(it)

                        }

                    }
                }

                else{
                    log("No Supporter ")
                }

                isHasSupporter()
                dismissProgressDialog()
            }
        }
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
    }

    private fun isHasSupporter() {
        if (sharedPreferences.getBoolean(HAS_PARTNER)) {
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
        sharedUserDialog = Dialog(requireContext())
        sharedUserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogAddSupporterBinding.inflate(layoutInflater)
        sharedUserDialog.setContentView(dialogBinding.root)
        sharedUserDialog.setCanceledOnTouchOutside(true)
        if (sharedPreferences.getInt(NUMBER_SUPPORTERS) >= 3) {
            dialogBinding.icClose.visibility = View.GONE
        }
        val window = sharedUserDialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        sharedUserDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBinding.btAddNew.setOnClickListener {
            sharedUserDialog.dismiss()
            findNavController().navigate(R.id.action_supportesFragment_to_addSupporterFragment)
        }
        dialogBinding.icClose.setOnClickListener {
            sharedUserDialog.dismiss()
        }
        dialogBinding.btRecovery.setOnClickListener {
            sharedUserDialog.dismiss()
            findNavController().navigate(R.id.action_supportesFragment_to_suppporterRecoveryFragment)
        }
        sharedUserDialog.show()
    }

    private fun showDialogCannotAdding() {
        sharedUserDialog = Dialog(requireContext())
        sharedUserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogCannotAddSupporterBinding.inflate(layoutInflater)
        sharedUserDialog.setContentView(dialogBinding.root)
        sharedUserDialog.setCanceledOnTouchOutside(true)
        val window = sharedUserDialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        sharedUserDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBinding.btOk.setOnClickListener {
            sharedUserDialog.dismiss()
        }
        dialogBinding.icClose.setOnClickListener {
            sharedUserDialog.dismiss()
        }
        sharedUserDialog.show()
    }



}
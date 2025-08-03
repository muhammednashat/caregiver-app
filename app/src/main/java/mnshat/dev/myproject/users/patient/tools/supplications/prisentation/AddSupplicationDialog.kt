package mnshat.dev.myproject.users.patient.tools.supplications.prisentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseDialogFragment
import mnshat.dev.myproject.databinding.DialogAddSupplicationsBinding
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.util.isValidInput

@AndroidEntryPoint
class AddSupplicationDialog : BaseDialogFragment() {

    private lateinit var binding: DialogAddSupplicationsBinding

    private lateinit var supplicationText: String
    private lateinit var numberOfRepetitionText: String
    private val viewModel: SupplicationViewModel by viewModels()



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
         localDialog = Dialog(requireContext())
        localDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogAddSupplicationsBinding.inflate(layoutInflater)
        localDialog.setContentView(binding.root)
        localDialog.setCanceledOnTouchOutside(true)

        val window = localDialog.window
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }
        setupClickListener()
        observeViewModel()
        return localDialog
    }


    private fun setupClickListener() {
        binding.icClose.setOnClickListener {
            localDialog.dismiss()
        }

        binding.addButton.setOnClickListener {
           if(checkValidation()){
            viewModel.storeUserSupplication(supplication())
           }
        }
    }

   private fun checkValidation():Boolean{
        supplicationText = binding.supplicationEditText.text.toString()
        numberOfRepetitionText = binding.numberEditText.text.toString()
       return if (!isValidInput(supplicationText)) {
           showToast(getString(R.string.please_write_the_supplication_that_you_want))
           false
       } else if (!isValidInput(numberOfRepetitionText) ) {
           showToast(getString(R.string.please_write_the_number_of_repetitions_you_want_to_reach))
           false
       } else if (numberOfRepetitionText.startsWith("0")) {
           showToast(getString(R.string.number_not_valid))
           false
       }
       else
           true

    }

    private fun supplication():Supplication {
        return Supplication(supplicationText, numberOfRepetitionText.toInt())
    }


    private fun observeViewModel() {
//        viewModel.isDismissProgressDialog.observe(viewLifecycleOwner) { isDismiss ->
//            if (isDismiss) {
//                localDialog.dismiss()
//                viewModel.resetIsDismissProgressDialog()
//            }
//    }

    }


}

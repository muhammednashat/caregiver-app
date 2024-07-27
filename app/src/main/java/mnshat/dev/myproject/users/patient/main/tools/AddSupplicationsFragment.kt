package mnshat.dev.myproject.users.patient.main.tools

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentAddAzcarBinding
import mnshat.dev.myproject.factories.ToolsViewModelFactory
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.isValidInput


class AddSupplicationsFragment : BaseBottomSheetDialogFragment<FragmentAddAzcarBinding>() {

    private lateinit var viewModel: ToolsViewModel
    private lateinit var nameSupplication:String
    private lateinit var numberSupplicationText:String
    private var isCheckedBox: Boolean = false
    private var numberSupplication:Int = 0


    override fun initializeViews() {
        intiBackgroundBasedOnLang()
    }

    override fun setupClickListener() {
        super.setupClickListener()

        binding.close.setOnClickListener{
            dismiss()
        }
        binding.addButton.setOnClickListener{
           if (validation()){
               showProgressDialog()
               viewModel.onAddSupplicationClick( getInstanceSupplication())
           }

        }
    }

    private fun observeViewModel() {

        viewModel.isDismissProgressDialog.observe(viewLifecycleOwner) { isDismissProgressDialog ->
            if (isDismissProgressDialog) {
                clearEditTexts()
                dismissProgressDialog()
                viewModel.resetIsDismissProgressDialog()
            }

        }

    }

    private fun clearEditTexts() {
        binding.nameEditText.text.clear()
        binding.numberEditText.text.clear()
        binding.infiniteRepeatCheckBox.isChecked = false
    }
    private fun validation(): Boolean {
        saveInputDataToVariables()
        return if (!isValidInput(nameSupplication)) {
            showToast(getString(R.string.please_write_the_supplication_that_you_want))
            false
        } else if (!isValidInput(numberSupplicationText) && !isCheckedBox) {
            showToast(getString(R.string.please_write_the_number_of_repetitions_you_want_to_reach))
            false
        } else if (numberSupplicationText.startsWith("0")) {
            showToast(getString(R.string.number_not_valid))
            false
        }
        else
            true
    }

    private fun saveInputDataToVariables() {
        nameSupplication = binding.nameEditText.text.toString()
        numberSupplicationText = binding.numberEditText.text.toString()
        isCheckedBox = binding.infiniteRepeatCheckBox.isChecked

    }

    private fun getInstanceSupplication():Supplication {
        numberSupplication = if (isCheckedBox) 0 else numberSupplicationText.toInt()
        return Supplication(nameSupplication, numberSupplication)
    }

    override fun getLayout()= R.layout.fragment_add_azcar

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ToolsViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[ToolsViewModel::class.java]
        binding.lifecycleOwner = this
        observeViewModel()

    }


    private fun intiBackgroundBasedOnLang() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
    }
}
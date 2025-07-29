package mnshat.dev.myproject.users.patient.tools.supplications.prisentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment2
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentAddAzcarBinding
import mnshat.dev.myproject.databinding.FragmentMainSupplicationsBinding
import mnshat.dev.myproject.factories.SupplicationsViewModelFactory
import mnshat.dev.myproject.interfaces.DataReLoader
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.isValidInput
import kotlin.getValue

@AndroidEntryPoint
class AddSupplicationsFragment : BaseFragment() {

    private val viewModel: SupplicationViewModel by viewModels()
    private lateinit var binding: FragmentAddAzcarBinding

    private lateinit var nameSupplication:String
    private lateinit var dataReLoader: DataReLoader
    private lateinit var numberSupplicationText:String
    private var isCheckedBox: Boolean = false
    private var isNeedingReload: Boolean = false
    private var numberSupplication:Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddAzcarBinding.inflate(inflater, container, false)
        observeViewModel()
        setupClickListener()
        return binding.root
    }
    private fun checkInternetConnection() {
        if (isConnected()) {

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


        binding.close.setOnClickListener{

        }
        binding.addButton.setOnClickListener{
           if (validation()){
               showProgressDialog()
               viewModel.onAddSupplicationClick( getInstanceSupplication())
           }

        }
    }


    override fun onStop() {
        super.onStop()
        if (isNeedingReload){
            dataReLoader.reloadData()
        }

    }

    fun setDataReLoader(dataReLoader: DataReLoader) {
        this.dataReLoader = dataReLoader
    }
    private fun observeViewModel() {

        viewModel.isDismissProgressDialog.observe(viewLifecycleOwner) { isDismissProgressDialog ->
            println("isDismissProgressDialog")
            isNeedingReload = true
            if (isDismissProgressDialog) {
                println("isDismissProgressDialog  if ")
                clearEditTexts()
                dismissProgressDialog()
                viewModel.resetIsDismissProgressDialog()
            }else{
                println("isDismissProgressDialog  else ")

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



}
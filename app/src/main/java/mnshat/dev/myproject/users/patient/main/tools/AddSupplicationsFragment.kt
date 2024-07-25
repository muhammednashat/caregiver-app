package mnshat.dev.myproject.users.patient.main.tools

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentAddAzcarBinding
import mnshat.dev.myproject.factories.ToolsViewModelFactory
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.util.ENGLISH_KEY
import kotlin.properties.Delegates


class AddSupplicationsFragment : BaseBottomSheetDialogFragment<FragmentAddAzcarBinding>() {

    private lateinit var viewModel: ToolsViewModel
    private lateinit var nameSupplication:String
    private lateinit var numberSupplicationText:String
    private var numberSupplication by Delegates.notNull<Int>()
    override fun initializeViews() {
        intiBackgroundBasedOnLang()
    }

    override fun setupClickListener() {
        super.setupClickListener()

        binding.close.setOnClickListener{
            dismiss()
        }
        binding.addButton.setOnClickListener{
            validation()
          viewModel.onAddSupplicationClick( getInstanceSupplication())
        }
    }

    private fun validation() {
        nameSupplication = binding.nameEditText.text.toString()
        numberSupplicationText = binding.numberEditText.text.toString()

        val isCheckedBox = binding.infiniteRepeatCheckBox.isChecked
        val numberSupplication = if (isCheckedBox) 0 else numberSupplicationText.toIntOrNull()
    }

    private fun getInstanceSupplication():Supplication {
        return Supplication(nameSupplication, numberSupplication)
    }

    override fun getLayout()= R.layout.fragment_add_azcar

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ToolsViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[ToolsViewModel::class.java]
        binding.lifecycleOwner = this

    }


    private fun intiBackgroundBasedOnLang() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
    }
}
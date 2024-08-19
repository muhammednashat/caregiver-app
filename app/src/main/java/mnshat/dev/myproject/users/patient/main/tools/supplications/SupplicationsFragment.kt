package mnshat.dev.myproject.users.patient.main.tools.supplications

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentSupplicationsBinding
import mnshat.dev.myproject.factories.SupplicationsViewModelFactory
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.getListHands
import mnshat.dev.myproject.util.getListSebha


class SupplicationsFragment : BasePatientFragment<FragmentSupplicationsBinding>() {

    private lateinit var viewModel: SupplicationsViewModel


    override fun initializeViews() {

    }

    fun setUiData(supplication: Supplication) {
        binding.textNameSupplication.text = supplication.name
        binding.textBaseNumber.text = supplication.number.toString()
    }

    private fun retrieveDataFromArguments() {

        val args: SupplicationsFragmentArgs by navArgs()
        viewModel.setSupplication(args.supplication)
    }

    override fun getLayout()= R.layout.fragment_supplications

    private fun observeViewModel() {
        viewModel.supplication.observe(viewLifecycleOwner) {
            setUiData(it)
        }

        viewModel.newImageSupplication.observe(viewLifecycleOwner) {
            binding.handImageView.setImageResource(it)
        }

        viewModel.numberRemaining.observe(viewLifecycleOwner) {
            binding.textRemaining.text = it.toString()
        }

    }


    override fun setupClickListener() {
        super.setupClickListener()

        binding.imageViewHand.setOnClickListener{
            changeFocusing(true)
            viewModel.setListImage(getListHands())
            viewModel.resetCounter()

        }

        binding.imageViewSebha.setOnClickListener{
            changeFocusing(false)
            viewModel.setListImage(getListSebha())
            viewModel.resetCounter()

        }

    }

    fun changeFocusing(isHand:Boolean){
    if (isHand){
        binding.imageViewHand.setBackgroundDrawable(resources.getDrawable(R.drawable.circle_blue_border_blue))
        binding.imageViewSebha.setBackgroundDrawable(resources.getDrawable(R.drawable.circle_blue2))
    }else{
        binding.imageViewHand.setBackgroundDrawable(resources.getDrawable(R.drawable.circle_blue2))
        binding.imageViewSebha.setBackgroundDrawable(resources.getDrawable(R.drawable.circle_blue_border_blue))

    }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        retrieveDataFromArguments()
        observeViewModel()

    }

    private fun initViewModel() {
        val factory = SupplicationsViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel =
            ViewModelProvider(requireActivity(), factory)[SupplicationsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    fun showFulltext(){

    }

}
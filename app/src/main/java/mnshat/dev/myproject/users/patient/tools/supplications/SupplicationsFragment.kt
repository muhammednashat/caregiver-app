package mnshat.dev.myproject.users.patient.tools.supplications

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.view.Window
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import mnshat.dev.myproject.R
import mnshat.dev.myproject.commonFeatures.sharingcontent.ChooseSupporterFragment
import mnshat.dev.myproject.databinding.DialogFullTextSupplicationBinding
import mnshat.dev.myproject.databinding.FragmentSupplicationsBinding
import mnshat.dev.myproject.factories.SupplicationsViewModelFactory
import mnshat.dev.myproject.interfaces.OnSendButtonClicked
import mnshat.dev.myproject.model.SharingContent
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.LIBRARY
import mnshat.dev.myproject.util.SUPPLICATIONS
import mnshat.dev.myproject.util.data.getListHands
import mnshat.dev.myproject.util.data.getListSebha


class SupplicationsFragment : BasePatientFragment<FragmentSupplicationsBinding>() ,
    OnSendButtonClicked {

    private lateinit var viewModel: SupplicationsViewModel
    private lateinit var fullTextSupplicationDialog: Dialog
     private lateinit var supplicationText: String



    private fun setUiData(supplication: Supplication) {
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
            supplicationText = it.name!!
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
        binding.icShowFullSupplication.setOnClickListener {
            showFullTextSupplicationDialog(supplicationText)
        }
        binding.icMore.setOnClickListener {
            showPopupMenu(it)
        }

        binding.imageViewSebha.setOnClickListener{
            changeFocusing(false)
            viewModel.setListImage(getListSebha())
            viewModel.resetCounter()

        }

    }

    private fun changeFocusing(isHand:Boolean){
    if (isHand){
        binding.imageViewHand.setBackgroundDrawable(resources.getDrawable(R.drawable.circle_blue_border_blue))
        binding.imageViewSebha.setBackgroundDrawable(resources.getDrawable(R.drawable.circle_blue2))
    }else{
        binding.imageViewHand.setBackgroundDrawable(resources.getDrawable(R.drawable.circle_blue2))
        binding.imageViewSebha.setBackgroundDrawable(resources.getDrawable(R.drawable.circle_blue_border_blue))

    }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireActivity(), view)
        popupMenu.inflate(R.menu.settings_supplicaion_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            handleMenuItemClick(menuItem)
        }
        popupMenu.show()
    }

    private fun handleMenuItemClick(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_sharing -> {
                navigateToChooseSupporter()



                true
            }
            else -> false
    }
}

    private fun navigateToChooseSupporter() {
        val fragment = ChooseSupporterFragment()
        fragment.initOnConfirmButtonClicked(this)
        fragment.show(childFragmentManager, ChooseSupporterFragment::class.java.name)
    }

    private fun showFullTextSupplicationDialog(supplicationText:String) {
        fullTextSupplicationDialog = Dialog(requireContext())
        fullTextSupplicationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val binding = DialogFullTextSupplicationBinding.inflate(layoutInflater)
        fullTextSupplicationDialog.setContentView(binding.root)
        fullTextSupplicationDialog.setCanceledOnTouchOutside(true)
        val window = fullTextSupplicationDialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        fullTextSupplicationDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.supplicationText.text = supplicationText
        binding.iconClose.setOnClickListener {
            fullTextSupplicationDialog.dismiss()
        }
        fullTextSupplicationDialog.show()
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


    private fun getSharingContent(list: MutableList<String>) =
        SharingContent(
            type =  SUPPLICATIONS,
            supplication = viewModel.supplication.value!!,
            supporters = list
        )


    override fun onSendClicked(list: MutableList<String>) {
        showProgressDialog()
        viewModel.shareContent(getSharingContent(list)){
            if (it == null){
                showToast("done")
            }else{
                showToast(it)
            }
            dismissProgressDialog()
        }

    }

}
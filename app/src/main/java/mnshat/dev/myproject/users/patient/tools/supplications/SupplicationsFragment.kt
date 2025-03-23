package mnshat.dev.myproject.users.patient.tools.supplications

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import mnshat.dev.myproject.R
import mnshat.dev.myproject.commonFeatures.posts.ChooseSupporterFragment
import mnshat.dev.myproject.databinding.DialogFullTextSupplicationBinding
import mnshat.dev.myproject.databinding.FragmentSupplicationsBinding
import mnshat.dev.myproject.factories.SupplicationsViewModelFactory
import mnshat.dev.myproject.interfaces.OnSendButtonClicked
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.SUPPLICATIONS
import mnshat.dev.myproject.util.data.getListHands
import mnshat.dev.myproject.util.data.getListSebha
import mnshat.dev.myproject.util.log
import java.util.Locale


class SupplicationsFragment : BasePatientFragment<FragmentSupplicationsBinding>() ,
    OnSendButtonClicked {

    private lateinit var viewModel: SupplicationsViewModel
    private lateinit var fullTextSupplicationDialog: Dialog
     private lateinit var supplicationText: String
    private var selectedSoundResId: Int = R.raw.tick1
    private var mediaPlayer: MediaPlayer? = null


    private fun setUiData(supplication: Supplication) {
        binding.textNameSupplication.text = supplication.name
        binding.textBaseNumber.text = getLocalizedNumber( supplication.number!!)
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
            if(it != 0){
                playTickSound()
            }
            binding.textRemaining.text = getLocalizedNumber(it)
        }

    }
    private fun getLocalizedNumber(number: Int): String {
        var  lang = sharedPreferences.getString(LANGUAGE) ;
        lang = if ((lang == null || lang.isEmpty())) "ar" else lang
        log(lang)

        return String.format(Locale(lang), "%d", number)
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
        if (sharedPreferences.getBoolean(HAS_PARTNER)){
            val fragment = ChooseSupporterFragment()
            fragment.initOnConfirmButtonClicked(this)
            fragment.show(childFragmentManager, ChooseSupporterFragment::class.java.name)
        }else{
            showToast(getString(R.string.no_supporters_text))
        }


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


    private fun post(list: MutableList<String>) =
        Post(
            type =  SUPPLICATIONS,
            supplication = viewModel.supplication.value!!,
            supporters = list
        )


    override fun onSendClicked(list: MutableList<String>) {
        showProgressDialog()
        viewModel.shareContent(post(list)){
            if (it == null){
                showToast("done")
            }else{
                showToast(it)
            }
            dismissProgressDialog()
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
    private fun playTickSound() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, R.raw.tick4)
        mediaPlayer?.start()
    }

    private fun stopTickSound() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
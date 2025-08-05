package mnshat.dev.myproject.users.patient.tools.supplications.prisentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.commonFeatures.posts.ChooseSupporterFragment
import mnshat.dev.myproject.databinding.DialogFullTextSupplicationBinding
import mnshat.dev.myproject.databinding.FragmentMainSupplicationsBinding
import mnshat.dev.myproject.databinding.FragmentSupplicationsBinding
import mnshat.dev.myproject.factories.SupplicationsViewModelFactory
import mnshat.dev.myproject.interfaces.OnSendButtonClicked
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.users.patient.BasePatientFragment
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.SUPPLICATIONS
import mnshat.dev.myproject.util.data.getListHands
import mnshat.dev.myproject.util.data.getListSebha
import mnshat.dev.myproject.util.log
import java.util.Locale
import kotlin.getValue

@AndroidEntryPoint
class SupplicationsFragment : BaseFragment(),
    OnSendButtonClicked {


    private val viewModel: SupplicationViewModel by activityViewModels()
    private lateinit var binding: FragmentSupplicationsBinding
    private lateinit var supplicationText: String

//    private lateinit var fullTextSupplicationDialog: Dialog
//    private var mediaPlayer: MediaPlayer? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSupplicationsBinding.inflate(inflater, container, false)
        setUiData(viewModel.selectedSupplication!!)
        observeViewModel()
        return binding.root
    }

    private fun setUiData(supplication: Supplication) {
        binding.textNameSupplication.text = supplication.name
//        binding.textBaseNumber.text = getLocalizedNumber( supplication.number!!)
        supplicationText = supplication.name!!
    }

    private fun observeViewModel() {

//        viewModel.newImageSupplication.observe(viewLifecycleOwner) {
//            binding.handImageView.setImageResource(it)
//        }
//
//        viewModel.numberRemaining.observe(viewLifecycleOwner) {
//            if(it != 0){
//                playTickSound()
//            }
//            binding.textRemaining.text = getLocalizedNumber(it)
//        }

    }
//
//    override fun setupClickListener() {
//        super.setupClickListener()
//        binding.icBack.setOnClickListener {
//            findNavController().popBackStack()
//        }
//
//
//        binding.imageViewHand.setOnClickListener{
//            changeFocusing(true)
//            viewModel.setListImage(getListHands())
//            viewModel.resetCounter()
//
//        }
//        binding.icShowFullSupplication.setOnClickListener {
//            showFullTextSupplicationDialog(supplicationText)
//        }
//        binding.icMore.setOnClickListener {
//            showPopupMenu(it)
//        }
//
//        binding.imageViewSebha.setOnClickListener{
//            changeFocusing(false)
//            viewModel.setListImage(getListSebha())
//            viewModel.resetCounter()
//
//        }
//
//    }
//
//    private fun changeFocusing(isHand:Boolean){
//    if (isHand){
//        binding.imageViewHand.setBackgroundDrawable(resources.getDrawable(R.drawable.circle_blue_border_blue))
//        binding.imageViewSebha.setBackgroundDrawable(resources.getDrawable(R.drawable.circle_blue2))
//    }else{
//        binding.imageViewHand.setBackgroundDrawable(resources.getDrawable(R.drawable.circle_blue2))
//        binding.imageViewSebha.setBackgroundDrawable(resources.getDrawable(R.drawable.circle_blue_border_blue))
//
//    }
//    }
//
//    private fun showPopupMenu(view: View) {
//        val popupMenu = PopupMenu(requireActivity(), view)
//        popupMenu.inflate(R.menu.settings_supplicaion_menu)
//        popupMenu.setOnMenuItemClickListener { menuItem ->
//            handleMenuItemClick(menuItem)
//        }
//        popupMenu.show()
//    }
//
//    private fun handleMenuItemClick(menuItem: MenuItem): Boolean {
//        return when (menuItem.itemId) {
//            R.id.menu_sharing -> {
//                navigateToChooseSupporter()
//                true
//            }
//            else -> false
//    }
//}
//
//    private fun navigateToChooseSupporter() {
//        if (sharedPreferences.getBoolean(HAS_PARTNER)){
//            val fragment = ChooseSupporterFragment()
//            fragment.initOnConfirmButtonClicked(this)
//            fragment.show(childFragmentManager, ChooseSupporterFragment::class.java.name)
//        }else{
//            showToast(getString(R.string.no_supporters_text))
//        }
//
//
//    }
//
//    private fun showFullTextSupplicationDialog(supplicationText:String) {
//        fullTextSupplicationDialog = Dialog(requireContext())
//        fullTextSupplicationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//
//        val binding = DialogFullTextSupplicationBinding.inflate(layoutInflater)
//        fullTextSupplicationDialog.setContentView(binding.root)
//        fullTextSupplicationDialog.setCanceledOnTouchOutside(true)
//        val window = fullTextSupplicationDialog.window
//        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        fullTextSupplicationDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        binding.supplicationText.text = supplicationText
//        binding.iconClose.setOnClickListener {
//            fullTextSupplicationDialog.dismiss()
//        }
//        fullTextSupplicationDialog.show()
//    }


//    private fun post(list: MutableList<String>) =
//        Post(
//            type =  SUPPLICATIONS,
//            supplication = viewModel.supplication.value!!,
//            supporters = list
//        )

//
    override fun onSendClicked(list: MutableList<String>) {
//        showProgressDialog()
//        viewModel.shareContent(post(list)){
//            if (it == null){
//                showToast("done")
//            }else{
//                showToast(it)
//            }
//            dismissProgressDialog()
//        }

    }
//    override fun onDestroy() {
//        super.onDestroy()
//        mediaPlayer?.release()
//        mediaPlayer = null
//    }
//    private fun playTickSound() {
//        mediaPlayer?.release()
//        mediaPlayer = MediaPlayer.create(context, R.raw.tick4)
//        mediaPlayer?.start()
//    }
//
//    private fun stopTickSound() {
//        mediaPlayer?.release()
//        mediaPlayer = null
//    }


}
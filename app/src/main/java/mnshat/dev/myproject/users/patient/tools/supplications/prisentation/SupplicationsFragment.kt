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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.posts.presentation.ChooseSupporterFragment
import mnshat.dev.myproject.posts.OnSendButtonClicked
import mnshat.dev.myproject.posts.presentation.PostsViewModel
import mnshat.dev.myproject.databinding.DialogFullTextSupplicationBinding
import mnshat.dev.myproject.databinding.FragmentSupplicationsBinding
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.util.LANGUAGE
import java.util.Locale
import kotlin.getValue

@AndroidEntryPoint
class SupplicationsFragment : BaseFragment() , OnSendButtonClicked {


    private val viewModel: SupplicationViewModel by activityViewModels()
    private val postsViewModel: PostsViewModel by viewModels()

    private lateinit var binding: FragmentSupplicationsBinding
    private lateinit var supplicationText: String
    private lateinit var fullTextSupplicationDialog: Dialog
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSupplicationsBinding.inflate(inflater, container, false)
        setUiData(viewModel.selectedSupplication!!)
        viewModel.resetCounter()

        observeViewModel()
        setupClickListener()
        return binding.root
    }

    private fun setUiData(supplication: Supplication) {
        binding.textNameSupplication.text = supplication.name
        binding.textBaseNumber.text = getLocalizedNumber( supplication.number!!)
        supplicationText = supplication.name!!
    }

    private fun getLocalizedNumber(number: Int): String {
        var  lang = viewModel.supplicationsRepo.sharedPreferences.getString(LANGUAGE)
        lang = if ((lang.isEmpty())) "ar" else lang
        return String.format(Locale(lang), "%d", number)
    }

    private fun observeViewModel() {
            postsViewModel.statusSharing.observe(viewLifecycleOwner){
                if (it){
              showToast(getString(R.string.shared_successfully))
                }
                dismissProgressDialog()
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
    private fun setupClickListener() {

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.constraintLayout.setOnClickListener {
           viewModel.resetCounter()
        }
        binding.handImageView.setOnClickListener {
          viewModel.onHandClick()
        }
        binding.imageViewHand.setOnClickListener{
            changeFocusing(true)
            viewModel.setListImage(viewModel.supplicationsRepo.handsList())
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
            viewModel.setListImage(viewModel.supplicationsRepo.sebhaList())
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
        if (!viewModel.supplicationsRepo.getUser().hasPartner!!){
            showToast(getString(R.string.no_supporters_text))

        } else if (!isConnected()) {
            showNoInternetSnackBar(binding.root)
        } else {
            val fragment = ChooseSupporterFragment()
            fragment.initOnConfirmButtonClicked(this)
            fragment.show(childFragmentManager, ChooseSupporterFragment::class.java.name)
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

    override fun onSendClicked(supporters: MutableList<String>) {
        showProgressDialog()
        postsViewModel.sharePost(viewModel.post(supporters))
    }


}
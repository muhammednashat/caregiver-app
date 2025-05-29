package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.DialogConfirmLogoutBinding
import mnshat.dev.myproject.databinding.FragmentSupportResponseBinding
import mnshat.dev.myproject.databinding.FragmentWhatShouldDoBinding
import mnshat.dev.myproject.databinding.FriendMessageDialogBinding

@AndroidEntryPoint

class SupportResponseFragment : Fragment() {

    private val viewModel: SupportCafeViewModel by viewModels()

    private lateinit var binding: FragmentSupportResponseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSupportResponseBinding.inflate(inflater,container, false)


        binding.suggestedSympathy.setOnClickListener {
            SuggestedPhrasesSympathyFragment().show(childFragmentManager, "")
        }
        binding.friendIdea.setOnClickListener {
            showDialogConfirmLogout()
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_supportResponseFragment_to_thanksFragment)
        }

        return binding.root
    }

    fun showDialogConfirmLogout() {
     val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = FriendMessageDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(true)

        val window = dialog.window
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }
//        dialogBinding.icClose.setOnClickListener {
//            dialog.dismiss()
//        }
//        dialogBinding.btnLogout.setOnClickListener {
//            logOut()
//            dialog.dismiss()
//        }
//        dialogBinding.btnCancel.setOnClickListener {
//            dialog.dismiss()
//        }
        dialog.show()
    }
    

}
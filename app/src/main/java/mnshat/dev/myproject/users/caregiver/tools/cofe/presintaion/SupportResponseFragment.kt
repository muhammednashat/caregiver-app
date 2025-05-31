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
import mnshat.dev.myproject.databinding.FragmentSupportResponseBinding
import mnshat.dev.myproject.databinding.FriendMessageDialogBinding

@AndroidEntryPoint

class SupportResponseFragment : Fragment() {

    private val viewModel: SupportCafeViewModel by viewModels()

    private lateinit var binding: FragmentSupportResponseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSupportResponseBinding.inflate(inflater, container, false)


        setUpListener()
          observeData()
        return binding.root
    }

    private fun observeData() {
        viewModel.selectedText.observe(viewLifecycleOwner) {
          val previousText = binding.editText.text.toString()
            binding.editText.setText(previousText+" "+ it)
        }
    }

    private fun setUpListener() {
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
    }

    private fun showDialogConfirmLogout() {
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
        dialogBinding.close.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


}
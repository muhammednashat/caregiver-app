package mnshat.dev.myproject.users.patient.tools

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.DialogBreathIntroBinding
import mnshat.dev.myproject.databinding.FragmentSupplicationsIntroBinding
import mnshat.dev.myproject.databinding.FragmentToolsBinding
import mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion.CofeActivity
import mnshat.dev.myproject.users.patient.tools.supplications.prisentation.SupplicationViewModel
import kotlin.getValue

@AndroidEntryPoint
class ToolsFragment : BaseFragment() {

    private lateinit var binding: FragmentToolsBinding
    private val viewModel: SupplicationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToolsBinding.inflate(inflater, container, false)
        setupClickListener()
        return binding.root
    }


    private fun setupClickListener() {

        binding.icBack.setOnClickListener {
            activity?.finish()

        }

        binding.imageCofe.setOnClickListener {
            startActivity(Intent(requireContext(), CofeActivity::class.java))
        }
        binding.imageSupplications.setOnClickListener {
            showDialog()

        }
        binding.imageBreath.setOnClickListener {
            showBreathIntroDialog(R.drawable.breath_intro){
                findNavController().navigate(R.id.action_toolsFragment_to_mainBreathFragment2)
            }
        }

        binding.imageGratitude.setOnClickListener {
            showBreathIntroDialog(R.drawable.gratitude_intro){
                findNavController().navigate(R.id.action_toolsFragment_to_gratitudeFragment2)
            }

        }

    }


    fun showBreathIntroDialog(image: Int, function: () -> Unit) {
     val  sharedDialog = Dialog(requireContext())
        sharedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogBreathIntroBinding.inflate(layoutInflater)
        dialogBinding.image.setImageResource(image)
        sharedDialog.setContentView(dialogBinding.root)
        sharedDialog.setCanceledOnTouchOutside(true)
        val window = sharedDialog.window
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }
        dialogBinding.icClose.setOnClickListener {
            sharedDialog.dismiss()
        }
        dialogBinding.start.setOnClickListener {
            function()
            sharedDialog.dismiss()
        }

        sharedDialog.show()
    }


    fun showDialog() {
        val  sharedDialog = Dialog(requireContext())
        sharedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = FragmentSupplicationsIntroBinding.inflate(layoutInflater)
        sharedDialog.setContentView(dialogBinding.root)
        sharedDialog.setCanceledOnTouchOutside(true)
        val window = sharedDialog.window
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }
        dialogBinding.icClose.setOnClickListener {
            sharedDialog.dismiss()
        }
        dialogBinding.start.setOnClickListener {
            findNavController().navigate(R.id.action_toolsFragment_to_mainSupplicationsFragment3)
            sharedDialog.dismiss()
        }

        sharedDialog.show()
    }



}
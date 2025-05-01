package mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.DialogConfirmUpdateReligionBinding
import mnshat.dev.myproject.databinding.FragmentStep2Binding
import mnshat.dev.myproject.databinding.FragmentStep3Binding
import mnshat.dev.myproject.util.RELIGION

class Step3Fragment : Fragment() {


    private lateinit var binding: FragmentStep3Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStep3Binding.inflate(inflater)

        binding.user.enter.setOnClickListener {

            findNavController().navigate(R.id.action_step3Fragment_to_step4Fragment)

        }
        binding.friend.enter.setOnClickListener {

//            findNavController().navigate(R.id.action_step3Fragment_to_friendIdeaEditingFragment)

        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root

    }

    fun chooseSupport() {
        val chooseUserDialog = Dialog(requireContext())
        chooseUserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogConfirmUpdateReligionBinding.inflate(layoutInflater)
        chooseUserDialog.setContentView(dialogBinding.root)
        chooseUserDialog.setCanceledOnTouchOutside(true)

        val window = chooseUserDialog.window
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }
        dialogBinding.icClose.setOnClickListener {
            chooseUserDialog.dismiss()
        }

        chooseUserDialog.show()

    }
}
package mnshat.dev.myproject.users.patient.main.tools

import android.view.View
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentAddAzcarBinding
import mnshat.dev.myproject.util.ENGLISH_KEY


class AddSupplicationsFragment : BaseBottomSheetDialogFragment<FragmentAddAzcarBinding>() {


    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))

        }
    }

    override fun getLayout()= R.layout.fragment_add_azcar


}
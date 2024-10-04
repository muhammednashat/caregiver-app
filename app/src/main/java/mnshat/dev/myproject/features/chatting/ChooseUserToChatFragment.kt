package mnshat.dev.myproject.features.chatting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentAgeBinding
import mnshat.dev.myproject.databinding.FragmentChooseUserToChatBinding
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.ENGLISH_KEY


class ChooseUserToChatFragment : BaseBottomSheetDialogFragment<FragmentChooseUserToChatBinding>() {
    override fun getLayout() = R.layout.fragment_choose_user_to_chat

    override fun setupClickListener() {
        binding.close.setOnClickListener {
            dismiss()
        }
    }

    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
    }

}
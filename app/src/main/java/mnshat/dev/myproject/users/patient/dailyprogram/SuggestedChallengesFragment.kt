package mnshat.dev.myproject.users.patient.dailyprogram

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentSuggestedChallengesBinding
import mnshat.dev.myproject.util.ENGLISH_KEY

class SuggestedChallengesFragment :
    BaseBottomSheetDialogFragment<FragmentSuggestedChallengesBinding>() {

    override fun getLayout() = R.layout.fragment_suggested_challenges

    private lateinit var viewMode: SharedDailyProgramViewModel


    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
    }


    override fun setupClickListener() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewMode = ViewModelProvider(requireActivity())[SharedDailyProgramViewModel::class.java]

    }

}
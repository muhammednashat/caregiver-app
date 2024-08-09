package mnshat.dev.myproject.users.patient.main.tools.breath

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.DurationAdapter
import mnshat.dev.myproject.auth.AuthViewModel
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentChooseDurationBreathBinding
import mnshat.dev.myproject.factories.AuthViewModelFactory
import mnshat.dev.myproject.factories.BreathViewModelFactory
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.ENGLISH_KEY


class ChooseDurationBreathFragment : BaseBottomSheetDialogFragment<FragmentChooseDurationBreathBinding>() {
    private lateinit var viewModel: BreathViewModel


    override fun getLayout() = R.layout.fragment_choose_duration_breath


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



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        setUpRecyclerview()

    }

    private fun setUpRecyclerview() {
     val adapter = DurationAdapter(viewModel.getListOfDurations())
        binding.recyclerViewDurations.adapter= adapter
    }

    private fun initViewModel() {
        val factory = BreathViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[BreathViewModel::class.java]
        binding.lifecycleOwner = this
    }
}
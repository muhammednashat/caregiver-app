package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentSuggestedPhrasesSympathyBinding

@AndroidEntryPoint
class SuggestedPhrasesSympathyFragment : BaseBottomSheetDialogFragment() {
    private val viewModel: SupportCafeViewModel by viewModels()
     private lateinit var adapter :SuggestedSympathyAdapter

     private lateinit var binding: FragmentSuggestedPhrasesSympathyBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSuggestedPhrasesSympathyBinding.inflate(inflater, container, false)

        initRecyclerView()
        return binding.root


    }

    private fun initRecyclerView() {
        val list = viewModel.getPhrases(requireContext())
        adapter = SuggestedSympathyAdapter(list)
        binding.recycler.adapter = adapter

    }


}
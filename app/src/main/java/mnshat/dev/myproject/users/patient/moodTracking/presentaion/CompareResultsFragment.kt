package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCompareResultsBinding

@AndroidEntryPoint
class CompareResultsFragment : BaseFragment() {

    private lateinit var binding: FragmentCompareResultsBinding
    private val viewModel: MoodViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompareResultsBinding.inflate(inflater, container, false)
        return  binding.root
    }

}
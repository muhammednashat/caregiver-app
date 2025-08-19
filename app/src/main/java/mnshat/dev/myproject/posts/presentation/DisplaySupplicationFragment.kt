package mnshat.dev.myproject.posts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentDisplaySupplicationBinding


class DisplaySupplicationFragment : BaseFragment() {

    private lateinit var binding: FragmentDisplaySupplicationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDisplaySupplicationBinding.inflate(inflater, container, false)
        val supplication = DisplaySupplicationFragmentArgs.fromBundle(requireArguments()).supplication
        binding.textView.text = supplication.name
 binding.icBack.setOnClickListener {
     findNavController().popBackStack()
 }

        return binding.root


    }

}
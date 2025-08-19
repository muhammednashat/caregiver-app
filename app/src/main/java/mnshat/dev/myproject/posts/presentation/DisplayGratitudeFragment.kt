package mnshat.dev.myproject.posts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentDisplayGratitudeBinding
import mnshat.dev.myproject.util.getGratitudeQuestionsList


class DisplayGratitudeFragment : BaseFragment() {


    private lateinit var binding: FragmentDisplayGratitudeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentDisplayGratitudeBinding.inflate(inflater, container, false)
        val gratitude = DisplayGratitudeFragmentArgs.fromBundle(requireArguments()).gratitude
        val questions = getGratitudeQuestionsList(requireActivity())
        binding.question.text = questions[gratitude.index]
        binding.text.text = gratitude.answer
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root


    }


}




package mnshat.dev.myproject.users.caregiver.tools

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentStepsBinding
import mnshat.dev.myproject.factories.CaregiverToolsViewModelFactory
import mnshat.dev.myproject.model.Step
import mnshat.dev.myproject.users.caregiver.main.BaseCaregiverFragment
import mnshat.dev.myproject.util.log


class StepsFragment : BaseCaregiverFragment<FragmentStepsBinding>() {

    override fun getLayout() = R.layout.fragment_steps
    private lateinit var viewModel: CaregiverToolsViewModel


    override fun setupClickListener() {
        super.setupClickListener()

        binding.constraintNext.setOnClickListener {
            log("${viewModel.getCurrentIndex()} ...  ${viewModel.getCurrentList().size}")
            if (viewModel.getCurrentIndex() < viewModel.getCurrentList().size-1) {
                viewModel.setCurrentIndex(viewModel.getCurrentIndex() + 1)
                updateUi(viewModel.getCurrentIndex(), viewModel.getCurrentList().size)
                updateUiData(viewModel.getCurrentIndex(), viewModel.getCurrentList())
            } else {
                findNavController().popBackStack()
            }
        }

        binding.back.setOnClickListener {
            viewModel.setCurrentIndex(viewModel.getCurrentIndex() - 1)
            updateUi(viewModel.getCurrentIndex(), viewModel.getCurrentList().size)
            updateUiData(viewModel.getCurrentIndex(), viewModel.getCurrentList())
        }

        binding.iconBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory =
            CaregiverToolsViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel =
            ViewModelProvider(requireActivity(), factory)[CaregiverToolsViewModel::class.java]

        updateUi(viewModel.getCurrentIndex(), viewModel.getCurrentList().size)
        updateUiData(viewModel.getCurrentIndex(), viewModel.getCurrentList())

    }

    private fun updateUi(steps: Int, size: Int) {
        when (steps) {
            0 -> {
                binding.textNext.text = getString(R.string.explore_more)
                binding.next.visibility = View.GONE
                binding.back.visibility = View.GONE
            }

            in 1 until size - 1 -> {
                binding.textNext.text = getString(R.string.next_step)
                binding.next.visibility = View.VISIBLE
                binding.back.visibility = View.VISIBLE
            }

            size - 1 -> {
                binding.textNext.text = getString(R.string.finish)
                binding.next.visibility = View.VISIBLE
                binding.next.setImageResource(R.drawable.heart)
                binding.back.visibility = View.GONE
            }
        }
    }

    private fun updateUiData(index: Int, steps: List<Step>) {
        val step = steps[index]
        binding.stepNumber.text = step.step
        binding.title.text = step.title
        binding.description.text = step.description
        binding.image.setImageResource(step.image)

        if (step.toDo.isNotEmpty()) {
            binding.toDo.text = step.toDo
            if(step.flag==1){
                binding.labelToDo.text = getString(R.string.to_do)
            }else{
                binding.labelToDo.text = getString(R.string.advice)
            }
            binding.toDo.visibility = View.VISIBLE
            binding.linearToDo.visibility = View.VISIBLE

        }else{
            binding.toDo.visibility = View.GONE
            binding.linearToDo.visibility = View.GONE
        }
        binding.scrollView.scrollTo(0,0)


    }

}
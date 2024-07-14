package mnshat.dev.myproject.users.patient.intro

import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentIntro3Binding
import mnshat.dev.myproject.users.patient.main.BaseUserFragment


class Intro3Fragment : BaseUserFragment<FragmentIntro3Binding>() {

    override fun getLayout()= R.layout.fragment_intro3

    override fun initializeViews() {
    }

    override fun setupClickListener() {
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_intro3Fragment_to_intro4Fragment)
        }
        binding.btnPrevious.setOnClickListener {
            findNavController().popBackStack()
        }
    }



    override fun onStart() {
        super.onStart()
    }
    override fun onResume() {
        super.onResume()
        setupSpinner()

    }
    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.array_status, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
//        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parentView: AdapterView<*>,
//                selectedItemView: View,
//                position: Int,
//                id: Long
//            ) {
//            }
//
//            override fun onNothingSelected(parentView: AdapterView<*>?) {
//            }
//        }
    }


}
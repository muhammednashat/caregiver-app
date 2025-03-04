package mnshat.dev.myproject.users.patient.main.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment2
import mnshat.dev.myproject.databinding.FragmentOnBoardingBinding


class OnBoardingFragment : BaseBottomSheetDialogFragment() {

    private  lateinit var binding:FragmentOnBoardingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOnBoardingBinding.inflate(inflater,container,false)

        initViewPager()

        return  binding.root
    }

    private fun initViewPager() {

//        val dotsIndicator = findViewById<WormDotsIndicator>(R.id.dots_indicator)

        val images = listOf(R.drawable.image_on_bording1, R.drawable.image_on_bording2,R.drawable.image_on_bording3, R.drawable.image_on_bording4)
        val adapter = OnboardingAdapter(images)
        binding.viewPager.adapter = adapter
//        dotsIndicator.attachTo(viewPager)
    }

}
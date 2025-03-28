package mnshat.dev.myproject.users.patient.profile.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentSettingsBinding
import mnshat.dev.myproject.util.ARABIC_KEY
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.SCHEDULING_TIME
import mnshat.dev.myproject.util.SplashActivity
import mnshat.dev.myproject.util.log

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: ProfileViewModel by viewModels()
    private var _currentLang = ""
    private var _updatingLang = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        initializeViews()
        setUpListeners()
        return binding.root

    }

    private fun setUpListeners() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.button.setOnClickListener {
         if (_currentLang == _updatingLang){
             activity?.onBackPressed()
         }else{
             changeLang(_updatingLang)
         }
        }
    }

    private fun initializeViews() {
        _currentLang = viewModel.sharedPreferences.getString(LANGUAGE)

        if (_currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            _currentLang = ARABIC_KEY
        }
        _updatingLang= _currentLang
        setUpRadioGroup()
        setUpReminderRadioGroup()
    }


    private fun setUpReminderRadioGroup(){
      val schedulingTime =   viewModel.sharedPreferences.getInt(SCHEDULING_TIME , 7)
      when(schedulingTime){
          7 -> binding.rbMorning.isChecked = true // 7 am
          13 -> binding.rbAfternoon.isChecked = true // 1pm
          20 -> binding.rbEvening.isChecked = true // 8 pm
      }

        binding.groupRoot2.setOnCheckedChangeListener {  group, checkedId ->
            when(checkedId){
                binding.rbMorning.id -> viewModel.sharedPreferences.storeInt(SCHEDULING_TIME,7)
                binding.rbAfternoon.id -> viewModel.sharedPreferences.storeInt(SCHEDULING_TIME,13)
                binding.rbEvening.id ->viewModel.sharedPreferences.storeInt(SCHEDULING_TIME,20)
            }

        }
    }
    private fun setUpRadioGroup() {
        if (_currentLang == ENGLISH_KEY) {
            binding.rbEnglish.isChecked = true
        } else {
            binding.rbArabic.isChecked = true
        }
        binding.groupRoot.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.rbEnglish.id -> {
                    _updatingLang = ENGLISH_KEY
                }

                binding.rbArabic.id -> {
                    _updatingLang = ARABIC_KEY
                }
            }

        }
    }

    private fun changeLang(lang: String) {
        viewModel.sharedPreferences.storeString(LANGUAGE, lang)
        startActivity(Intent(activity, SplashActivity::class.java))
        activity?.finish()
    }
}
package mnshat.dev.myproject.users.patient.supporters

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentReportingPermissionBinding
import mnshat.dev.myproject.model.Permissions
import mnshat.dev.myproject.util.ENGLISH_KEY


class ReportingPermissionFragment : BaseSupporterFragment<FragmentReportingPermissionBinding>() {

    private lateinit var permissions: Permissions
    override fun getLayout() = R.layout.fragment_reporting_permission
    override fun initializeViews() {
        val args: ReportingPermissionFragmentArgs by navArgs()
        permissions = args.permission
        setupUi(permissions)

        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
    }

    override fun setupClickListener() {
        binding.viewReporting.setOnCheckedChangeListener {
                buttonView, isChecked ->
            if (isChecked){
                binding.typeReport.visibility = View.VISIBLE
                binding.rangeReport.visibility = View.VISIBLE
            }else{
                binding.typeReport.visibility = View.GONE
                binding.rangeReport.visibility = View.GONE
            }

        }
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun setupUi(permissions: Permissions) {
        permissions?.let {
            if (it.allowViewReports) {
                binding.viewReporting.isChecked = true
                binding.typeReport.visibility = View.VISIBLE
                binding.rangeReport.visibility = View.VISIBLE
           when (it.typeReports){
               1 -> binding.moodReportOnly.isChecked =true
               2 -> binding.psychologicalReportOnly.isChecked =true
               3 -> binding.allType.isChecked =true
           }
           when (it.timeRangReports){
                    1 -> binding.weeklyReportsOnly.isChecked =true
                    2 -> binding.monthlyReportsOnly.isChecked =true
                    3 -> binding.annualReportsOnly.isChecked =true
                    4 -> binding.allRange.isChecked =true
                }

            }
        }
    }


}
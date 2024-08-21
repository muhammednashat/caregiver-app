package mnshat.dev.myproject.maybeneedlater

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentDailyProgramBinding
import mnshat.dev.myproject.model.DayTask
import mnshat.dev.myproject.model.Task
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ENGLISH_KEY


class DailyProgramFragment : BasePatientFragment<FragmentDailyProgramBinding>() {


    override fun getLayout() = R.layout.fragment_daily_program
    override fun initializeViews() {

        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
    }

    override fun setupClickListener() {
        binding.btnStart.setOnClickListener {



        }

//    private fun updateStatusDailyProgram(key: String) {
//       val data = StatusDailyProgram(1 , 1,0,0,0,0)
//        showProgressDialog()
//        val map = mapOf<String, Any>(key to data)
//        FirebaseService.updateItems(FirebaseService.userId, map) {
//            if (it) {
////                showToast()
////                findNavController().popBackStack()
//            } else {
//                showToast(getString(R.string.update_failed))
//            }
//            dismissProgressDialog()
//
//        }
//    }

    }
}


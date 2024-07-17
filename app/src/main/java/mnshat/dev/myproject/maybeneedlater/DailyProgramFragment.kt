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
            val db = Firebase.firestore
            val dayTask = DayTask()

            val task1 = Task()
            val task2 = Task()
            val task3 = Task()
            val task4 = Task()
            val task5 = Task()

            task1.type = 1
            task1.arTitle = "تحدى اليوم"
            task1.enTitle = "challenge today"
            task1.arDescription =
                "فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام"
            task1.enDescription =
                "In this task you have to do In this task you have to do In this task you have to do In this task you have to do"
            task1.image =
                "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2F71GNZ%2Bf6zyL._AC_SL1500_.jpg?alt=media&token=bfeb8c1b-0e47-420a-8cb7-3161aab2c1d6"
            task1.gender = 1
            task1.ageGroup = 1
            task1.religion = true

            task2.type = 2
            task2.arTitle = "مشاهدة الفديو"
            task2.enTitle = "See Video"
            task2.arDescription =
                "فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام"
            task2.enDescription =
                "In this task you have to do In this task you have to do In this task you have to do In this task you have to do"
            task2.link =
                "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/videos%2F3%20_Binary%20Search%20-%20Recursive%20implementation(720P_HD).mp4?alt=media&token=78e30d71-1b51-4ff6-9d0b-de3f6365aff1"
            task2.gender = 1
            task2.ageGroup = 1
            task2.religion = true


            task3.type = 3
            task3.arTitle = "اﻹستماع الى البودكاست"
            task3.enTitle = "Listening to podcast"
            task3.arDescription =
                "فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام"
            task3.enDescription =
                "In this task you have to do In this task you have to do In this task you have to do In this task you have to do"
            task3.link =
                "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/audios%2F%D8%B9%D9%84%D9%89%20%D9%82%D8%AF%D8%B1%20%D8%A7%D9%87%D9%84%20%D8%A7%D9%84%D8%B9%D8%B2%D9%85.mp3?alt=media&token=bf6e8bbc-a897-4f04-ae55-5b4922aaa1c2"
            task3.image =
                "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2F71GNZ%2Bf6zyL._AC_SL1500_.jpg?alt=media&token=bfeb8c1b-0e47-420a-8cb7-3161aab2c1d6"
            task3.gender = 1
            task3.ageGroup = 1
            task3.religion = true

            task4.type = 1
            task4.arTitle = "تحدى اليوم"
            task4.enTitle = "challenge today"
            task4.arDescription =
                "فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام"
            task4.enDescription =
                "In this task you have to do In this task you have to do In this task you have to do In this task you have to do"
            task4.link =
                "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/audios%2F%D8%B9%D9%84%D9%89%20%D9%82%D8%AF%D8%B1%20%D8%A7%D9%87%D9%84%20%D8%A7%D9%84%D8%B9%D8%B2%D9%85.mp3?alt=media&token=bf6e8bbc-a897-4f04-ae55-5b4922aaa1c2"
            task4.image =
                "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2Fimg1.jpg?alt=media&token=d0823e7a-416f-4798-ac0a-ee91976c8082"
            task4.gender = 1
            task4.ageGroup = 1
            task4.religion = true

            task5.type = 4
            task5.arText = "من هو الشخص الذى اذا يستمع إليك حقا عندما تتحدث وكيف يؤثر عليك ذلك ؟"
            task5.enText = "Who is the person who really listens to you when they call and how does this affect you?"
            task5.gender = 1
            task5.ageGroup = 1
            task5.religion = true


            dayTask.contemplation = listOf(task1, task2, task3, task4)
            dayTask.activity = listOf(task1, task1, task1, task1,task1)
            dayTask.spiritual = listOf(task1, task2, task3, task4)
            dayTask.behavior = listOf(task1, task2, task3, task4)
            dayTask.Gratitude = listOf(task5, task5, task5, task5)


            for (x in 1..30) {
                db.collection("tasks").document("" + x).set(dayTask)
            }

            db.document("tasks/1").get().addOnSuccessListener { it ->

            }
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


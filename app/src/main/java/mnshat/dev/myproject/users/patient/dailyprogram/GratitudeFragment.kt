package mnshat.dev.myproject.users.patient.dailyprogram

import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentGratitudeBinding
import mnshat.dev.myproject.model.Gratitude

class GratitudeFragment: BaseDailyProgramFragment<FragmentGratitudeBinding>() {


    override fun getLayout() = R.layout.fragment_gratitude
    override fun initializeViews() {

//        if (currentLang != ENGLISH_KEY) {
//            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
//        }
//        _currentTask = getCurrentTask()!!
//        listOfTasks = _currentTask.dayTask?.Gratitude as List<Task>
//        if (listOfTasks.size == 1) binding.btnRecommend.visibility = View.GONE
//        status = _currentTask.status!!
//        if (status.gratitude == 2){
//            binding.btnDone.visibility = View.GONE
//        }
//        getTask(status.currentIndexGratitude!!)
    }

    override fun setupClickListener() {
//        binding.btnFinish.setOnClickListener{
//              val question = binding.tvQuestion.text.toString()
//              val answer = binding.edtAnswer.text.toString()
//              addGratitudeRemotely(FirebaseService.userEmail!!,Gratitude(question,answer)
//              )
//        }
//        binding.icBack.setOnClickListener{
//            requireActivity().finish()
//        }
//        binding.btnRecommend.setOnClickListener{
//            val currentIndex=  getNextTask(status.currentIndexGratitude!!)
//            status.currentIndexGratitude = currentIndex
//            updateCurrentTaskLocally()
//        }
//        binding.btnPrevious.setOnClickListener{
//            findNavController().popBackStack()
//        }


    }

    private fun addGratitudeRemotely(email: String, gratitude: Gratitude) {
//        showProgressDialog()
//        val userId = FirebaseService.userId
//        FirebaseService.retrieveUser(null,email) { user ->
//            if (user != null) {
//                val currentList = user.gratitudeList ?: emptyList()
//                val updatedList = currentList + gratitude
//                val mapGratitude = mapOf<String, Any>(GRATITUDE_LIST to updatedList)
//                FirebaseService.updateItemsProfileUser(userId, mapGratitude) { isSuccess ->
//                    if (isSuccess) {
////                        retrieveNextDay(status.day?.plus(1).toString())
//                    } else {
//                        dismissProgressDialog()
//                    }
//                }
//            } else {
//                dismissProgressDialog()
//            }
//        }
    }
    private fun getNextTask(index: Int):Int {
//        return  if (listOfTasks.isNotEmpty()) {
//            val currentTaskIndex = (index + 1) % listOfTasks.size
//            getTask(currentTaskIndex)
//            currentTaskIndex
//        }
    //        else 0
    return 0
    }




    private fun getTask(index: Int){
//        task = listOfTasks[index]
//        task.let{
//            if (currentLang != ENGLISH_KEY){
//                binding.tvQuestion.text = task.arText
//            }else{
//                binding.tvQuestion.text = task.enText
//            }
//        }

    }



}

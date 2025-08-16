package mnshat.dev.myproject.users.patient.tools.gratitude.presentaion

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.commonFeatures.posts.ChooseSupporterFragment
import mnshat.dev.myproject.databinding.DialogShareContentBinding
import mnshat.dev.myproject.databinding.FragmentGratitudeBinding
import mnshat.dev.myproject.interfaces.OnConfirmButtonClicked
import mnshat.dev.myproject.interfaces.OnSendButtonClicked
import mnshat.dev.myproject.model.Gratitude
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.users.patient.BasePatientFragment
import mnshat.dev.myproject.util.GRATITUDE
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.getGratitudeQuestionsList
import mnshat.dev.myproject.util.isValidInput
import mnshat.dev.myproject.util.log
import kotlin.getValue
import kotlin.random.Random

@AndroidEntryPoint

class GratitudeFragment : BaseFragment(), OnConfirmButtonClicked, OnSendButtonClicked {

    private val viewModel: GratitudeViewModel by activityViewModels()
private  lateinit var  binding:FragmentGratitudeBinding
    private lateinit var currentQuestion: String

    private lateinit var answer:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      binding = FragmentGratitudeBinding.inflate(inflater, container, false)
        setupClickListener()

        initializeViews()
        return  binding.root

    }


     fun initializeViews() {
         setText(getRandomQuestion())

    }

    private fun setText(text: String) {
        currentQuestion = text
        binding.tvQuestion.text = text
    }


    private fun getRandomQuestion(): String {
        val questions = getGratitudeQuestionsList(requireActivity())
        val randomNumber = Random.nextInt(questions.size.minus(1))
        log("$randomNumber")
        viewModel.setSelectedPosition(randomNumber )
        return questions[randomNumber]
    }


    private fun setupClickListener() {


        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSend.setOnClickListener {
            val answer = binding.edtAnswer.text.toString()
            if ( isValidation(answer)){
                showProgressDialog()
                addGratitude(Gratitude(index = viewModel.getSelectedPosition(), answer = answer))
            }

        }

        binding.btnRecommend.setOnClickListener {
            SuggestedGratitudeQuestionsFragment(this).show(
                childFragmentManager,
                SuggestedGratitudeQuestionsFragment::class.java.name
            )
        }
    }

private fun addGratitude(gratitude: Gratitude) {
    viewModel.sendGratitude(gratitude){
        if (it){
            showToast(getString(R.string.your_answer_has_been_sent))
            answer = binding.edtAnswer.text.toString()
            clearText()
            showSharingDialog()
        }else{
        }
        dismissProgressDialog()
    }


}
 private fun clearText(){
     binding.edtAnswer.text.clear()
 }
    private fun isValidation(text: String) :Boolean{
      return   if (!isValidInput(text)) {
            showToast(getString(R.string.should_answer_question))
            false
        } else {
            true
        }
    }

    override fun onConfirmClicked(text: String) {
        setText(text)
    }



    private fun showSharingDialog() {

        sharedDialog = Dialog(requireContext())
        sharedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogShareContentBinding.inflate(layoutInflater)
        sharedDialog.setContentView(dialogBinding.root)
        sharedDialog.setCanceledOnTouchOutside(true)
        val window = sharedDialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        sharedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btOk.setOnClickListener {
//
//            if (sharedPreferences.getBoolean(HAS_PARTNER)){
//                navigateToChooseSupporter()
//            }else{
//                showToast(getString(R.string.no_supporters_text))
//            }

        }

        dialogBinding.icClose.setOnClickListener {
            sharedDialog.dismiss()
        }
        sharedDialog.show()
    }





    private fun navigateToChooseSupporter() {
        sharedDialog.dismiss()

        val fragment = ChooseSupporterFragment()
        fragment.initOnConfirmButtonClicked(this)
        fragment.show(childFragmentManager, ChooseSupporterFragment::class.java.name)
    }

    private fun getSharingContent(list: MutableList<String>) =
        Post(
            type =  GRATITUDE,
            gratitude = Gratitude(index = viewModel.getSelectedPosition(), answer = answer),
            supporters = list
        )

    override fun onSendClicked(list: MutableList<String>) {
        showProgressDialog()
        viewModel.shareContent(getSharingContent(list)){
            if (it == null){
                showToast("done")
            }else{
                showToast(it)
            }
            dismissProgressDialog()
        }

    }


}
package mnshat.dev.myproject.users.patient.tools.gratitude

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.commonFeatures.posts.ChooseSupporterFragment
import mnshat.dev.myproject.databinding.DialogShareContentBinding
import mnshat.dev.myproject.databinding.FragmentGratitudeBinding
import mnshat.dev.myproject.factories.GratitudeViewModelFactory
import mnshat.dev.myproject.interfaces.OnConfirmButtonClicked
import mnshat.dev.myproject.interfaces.OnSendButtonClicked
import mnshat.dev.myproject.model.Gratitude
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.GRATITUDE
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.getGratitudeQuestionsList
import mnshat.dev.myproject.util.isValidInput
import mnshat.dev.myproject.util.log
import kotlin.random.Random


class GratitudeFragment : BasePatientFragment<FragmentGratitudeBinding>(), OnConfirmButtonClicked,
    OnSendButtonClicked {
    private lateinit var viewModel: GratitudeViewModel
private lateinit var answer:String
    override fun getLayout() = R.layout.fragment_gratitude

    private lateinit var currentQuestion: String

    override fun initializeViews() {
        intiBackgroundBasedOnLang()
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


    private fun intiBackgroundBasedOnLang() {
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
    }

    override fun setupClickListener() {
        super.setupClickListener()

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        setText(getRandomQuestion())

    }


    private fun initViewModel() {
        val factory = GratitudeViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[GratitudeViewModel::class.java]
        binding.lifecycleOwner = this
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
            if (sharedPreferences.getBoolean(HAS_PARTNER)){
                navigateToChooseSupporter()
            }else{
                showToast(getString(R.string.no_supporters_text))
            }
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
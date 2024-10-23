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
import mnshat.dev.myproject.commonFeatures.sharingcontent.ChooseSupporterFragment
import mnshat.dev.myproject.databinding.DialogCannotAddSupporterBinding
import mnshat.dev.myproject.databinding.DialogShareContentBinding
import mnshat.dev.myproject.databinding.FragmentGratitudeBinding
import mnshat.dev.myproject.factories.GratitudeViewModelFactory
import mnshat.dev.myproject.interfaces.OnConfirmButtonClicked
import mnshat.dev.myproject.model.Gratitude
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.getGratitudeQuestionsList
import mnshat.dev.myproject.util.isValidInput
import mnshat.dev.myproject.util.log
import kotlin.random.Random


class GratitudeFragment : BasePatientFragment<FragmentGratitudeBinding>(), OnConfirmButtonClicked {
    private lateinit var viewModel: GratitudeViewModel

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
            clearText()
            showToast(getString(R.string.your_answer_has_been_sent))
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

        sharedUserDialog = Dialog(requireContext())
        sharedUserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogShareContentBinding.inflate(layoutInflater)
        sharedUserDialog.setContentView(dialogBinding.root)
        sharedUserDialog.setCanceledOnTouchOutside(true)
        val window = sharedUserDialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        sharedUserDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btOk.setOnClickListener {
            sharedUserDialog.dismiss()

            ChooseSupporterFragment().show(childFragmentManager, ChooseSupporterFragment::class.java.name)

//            viewModel.shareGratituse()
        }

        dialogBinding.icClose.setOnClickListener {
            sharedUserDialog.dismiss()
        }
        sharedUserDialog.show()
    }


}
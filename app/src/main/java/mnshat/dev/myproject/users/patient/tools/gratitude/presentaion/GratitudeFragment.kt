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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.posts.presentation.ChooseSupporterFragment
import mnshat.dev.myproject.databinding.DialogShareContentBinding
import mnshat.dev.myproject.databinding.FragmentGratitudeBinding
import mnshat.dev.myproject.interfaces.OnConfirmButtonClicked
import mnshat.dev.myproject.posts.OnSendButtonClicked
import mnshat.dev.myproject.posts.presentation.PostsViewModel
import mnshat.dev.myproject.users.patient.tools.gratitude.entity.Gratitude
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.util.GRATITUDE
import mnshat.dev.myproject.util.isValidInput
import mnshat.dev.myproject.util.log
import kotlin.getValue

@AndroidEntryPoint

class GratitudeFragment : BaseFragment(), OnConfirmButtonClicked, OnSendButtonClicked {

    private val viewModel: GratitudeViewModel by activityViewModels()
    private val postsViewModel: PostsViewModel by viewModels()

    private  lateinit var  binding:FragmentGratitudeBinding
    private lateinit var answer:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGratitudeBinding.inflate(inflater, container, false)
        setText(viewModel.getRandomQuestion(requireActivity()))
        setupClickListener()
        observeViewModel()
        return  binding.root

    }

    private fun observeViewModel() {
        postsViewModel.statusSharing.observe(viewLifecycleOwner){
            if (it){
                showToast(getString(R.string.shared_successfully))
            }
            dismissProgressDialog()
        }
    }

    private fun setText(text: String) {

        binding.tvQuestion.text = text
    }

    private fun setupClickListener() {


        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSend.setOnClickListener {
            val answer = binding.edtAnswer.text.toString()
            if ( isValidation(answer)){
                checkConnection ()
            }
        }

        binding.btnRecommend.setOnClickListener {
            SuggestedGratitudeQuestionsFragment(this).show(
                childFragmentManager,
                SuggestedGratitudeQuestionsFragment::class.java.name
            )
        }
    }

    private fun checkConnection() {
        if(isConnected()){
            showProgressDialog()
            addGratitude(Gratitude(index = viewModel.getSelectedPosition(), answer = binding.edtAnswer.text.toString()))

        }else{
            showNoInternetSnackBar(binding.root)
        }
    }

    private fun addGratitude(gratitude: Gratitude ) {
        viewModel.saveGratitudeRemotely(gratitude){
            if (it){
                showToast(getString(R.string.your_answer_has_been_sent))
                answer = binding.edtAnswer.text.toString()
                clearText()
                showSharingDialog()
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
            if (viewModel.gratitudeRepo.user.hasPartner!!){
                navigateToChooseSupporter()
                sharedDialog.dismiss()
            }else{
                showToast(getString(R.string.no_supporters_text))
                sharedDialog.dismiss()
            }

        }

        dialogBinding.icClose.setOnClickListener {
            sharedDialog.dismiss()
        }
        sharedDialog.show()
    }


    private fun navigateToChooseSupporter() {
        val fragment = ChooseSupporterFragment()
        fragment.initOnConfirmButtonClicked(this)
        fragment.show(childFragmentManager, ChooseSupporterFragment::class.java.name)
    }

    override fun onSendClicked(supporters: MutableList<String>) {
        log("onSendClicked $supporters")
        showProgressDialog()
        postsViewModel.sharePost(post(supporters))
    }

    private fun post(supporters: MutableList<String>) =
        Post(
            type =  GRATITUDE,
            gratitude = Gratitude(index = viewModel.getSelectedPosition(), answer = answer),
            supporters = supporters
        )




}
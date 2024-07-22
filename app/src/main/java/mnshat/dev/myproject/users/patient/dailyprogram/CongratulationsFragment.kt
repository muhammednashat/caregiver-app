package mnshat.dev.myproject.users.patient.dailyprogram

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.view.Window
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.DialogeSurveyBinding
import mnshat.dev.myproject.databinding.FragmentCongratulationsBinding
import mnshat.dev.myproject.util.USER_NAME


class CongratulationsFragment: BaseDailyProgramFragment<FragmentCongratulationsBinding>() {

    override fun getLayout() = R.layout.fragment_congratulations

    override fun initializeViews() {
       val nameUser= sharedPreferences.getString(USER_NAME)
        binding.textView.text= getString(R.string.completion_message,nameUser)

        binding.viewLevel.setOnClickListener {
        }

        binding.btOk.setOnClickListener {
            showDialogSurvey()
        }
    }



    private fun showDialogSurvey() {
    val dialog  = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val dialogBinding = DialogeSurveyBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(true)
    val window = dialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btnSend.setOnClickListener {
            dialog.dismiss()
            activity?.finish()
        }

        dialogBinding.imgSatisfied.setOnClickListener {
            clearSelection(dialogBinding)
            dialogBinding.imgSatisfied.setImageResource(R.drawable.ic_gestures)
            dialogBinding.textSatisfied.setTextColor(getResources().getColor(R.color.black))
        }
        dialogBinding.imgOk.setOnClickListener {
            clearSelection(dialogBinding)
            dialogBinding.imgOk.setImageResource(R.drawable.ic_sweat1)
            dialogBinding.textOk.setTextColor(getResources().getColor(R.color.black))
        }
        dialogBinding.imgUnSatisfied.setOnClickListener {
            clearSelection(dialogBinding)
            dialogBinding.imgUnSatisfied.setImageResource(R.drawable.ic_cry1)
            dialogBinding.textUnSatisfied.setTextColor(getResources().getColor(R.color.black))
        }

        dialog.show()
    }


    private fun clearSelection(dialogBinding: DialogeSurveyBinding) {
        dialogBinding.imgOk.setImageResource(R.drawable.ic_sweat2)
        dialogBinding.imgSatisfied.setImageResource(R.drawable.ic_gestures1)
        dialogBinding.imgUnSatisfied.setImageResource(R.drawable.ic_cry)

        dialogBinding.textOk.setTextColor(getResources().getColor(R.color.white))
        dialogBinding.textSatisfied.setTextColor(getResources().getColor(R.color.white))
        dialogBinding.textUnSatisfied.setTextColor(getResources().getColor(R.color.white))

    }



}
package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import mnshat.dev.myproject.R


class CofeInstructionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cofe_instruction, container, false)
    }


    private fun styleText(lang:String) {
//        val textView = findViewById<TextView>(R.id.text2)
//        val text = getString(R.string.in_the_thought_restructuring)
//        val spannable = SpannableString(text)
//        val start = if(lang == "ar") text.indexOf("رفيقًا") else text.indexOf(" supportive")
//        val end =  if(lang == "ar") text.indexOf("في لحظة") else  text.indexOf(", but")
//
//        spannable.setSpan(ForegroundColorSpan(Color.parseColor("#204167")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spannable.setSpan(BackgroundColorSpan(Color.parseColor("#c2e3f8")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spannable.setSpan(AbsoluteSizeSpan(16, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spannable.setSpan(StyleSpan(android.graphics.Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        textView.text = spannable
    }

}
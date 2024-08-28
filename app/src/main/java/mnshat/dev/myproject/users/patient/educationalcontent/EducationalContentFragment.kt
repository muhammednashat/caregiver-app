package mnshat.dev.myproject.users.patient.educationalcontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentEducationalContentBinding
import mnshat.dev.myproject.users.patient.dailyprogram.BaseDailyProgramFragment


class EducationalContentFragment : BaseDailyProgramFragment<FragmentEducationalContentBinding>() {
    override fun getLayout() = R.layout.fragment_educational_content

}

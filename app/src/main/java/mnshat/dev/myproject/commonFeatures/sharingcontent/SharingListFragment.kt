package mnshat.dev.myproject.commonFeatures.sharingcontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentSharingListBinding
import mnshat.dev.myproject.users.patient.main.BasePatientFragment


class SharingListFragment : BasePatientFragment<FragmentSharingListBinding>() {

    override fun getLayout() = R.layout.fragment_sharing_list


}
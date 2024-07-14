package mnshat.dev.myproject.users.admin.settings

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import mnshat.dev.myproject.R
import mnshat.dev.myproject.auth.AuthActivity
import mnshat.dev.myproject.databinding.FragmentAdminSettingsBinding
import mnshat.dev.myproject.users.admin.AdminScreenActivity

class AdminSettingsFragment : Fragment() {

    private lateinit var viewModel: AdminSettingsViewModel

    private lateinit var binding:FragmentAdminSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_admin_settings, container, false)
     binding.logout.setOnClickListener{
         FirebaseAuth.getInstance().signOut()
         startActivity(Intent(requireActivity(), AuthActivity::class.java))
         AdminScreenActivity().finish()
     }
     return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdminSettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
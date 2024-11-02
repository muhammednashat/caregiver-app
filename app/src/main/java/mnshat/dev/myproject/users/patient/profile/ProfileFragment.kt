package mnshat.dev.myproject.users.patient.profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.DialogConfirmLogoutBinding
import mnshat.dev.myproject.databinding.FragmentProfileBinding
import mnshat.dev.myproject.users.patient.profile.editprofile.EditProfileActivity
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.users.patient.supporters.SupportersActivity
import mnshat.dev.myproject.util.USER_IMAGE
import mnshat.dev.myproject.util.USER_NAME
import mnshat.dev.myproject.util.loadImage

class ProfileFragment : BasePatientFragment<FragmentProfileBinding>() {

    override fun getLayout() = R.layout.fragment_profile
    override fun setupClickListener() {
        binding.editProfile.setOnClickListener {
            startActivity(Intent(requireActivity(), EditProfileActivity::class.java))
        }

        binding.sharedContent.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_postsFragment2)
        }
        binding.services.supporter.setOnClickListener {
            startActivity(Intent(requireActivity(), SupportersActivity::class.java))
        }

        binding.settings.setOnClickListener {

        }
        binding.gratitude.setOnClickListener {
           findNavController().navigate(R.id.action_profileFragment_to_gratitudeListFragment)
        }
        binding.logOut.setOnClickListener {

            showDialogConfirmLogout()
        }
    }

    override fun initializeViews() {
        super.initializeViews()
        binding.nameUser.text = sharedPreferences.getString(USER_NAME)
        loadImage(requireActivity(),sharedPreferences.getString(USER_IMAGE),binding.imageUser)
    }
    override fun onStart() {
        super.onStart()
        binding.nameUser.text = sharedPreferences.getString(USER_NAME)
    }

    private fun showDialogConfirmLogout() {
        sharedUserDialog = Dialog(requireContext())
        sharedUserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogConfirmLogoutBinding.inflate(layoutInflater)
        sharedUserDialog.setContentView(dialogBinding.root)
        sharedUserDialog.setCanceledOnTouchOutside(true)

        val window = sharedUserDialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        sharedUserDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.icClose.setOnClickListener {
            sharedUserDialog.dismiss()
        }
        dialogBinding.btnLogout.setOnClickListener {
            logOut()
            sharedUserDialog.dismiss()
        }
        dialogBinding.btnCancel.setOnClickListener {
            sharedUserDialog.dismiss()
        }
        sharedUserDialog.show()
    }


}
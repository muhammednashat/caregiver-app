package mnshat.dev.myproject.users.patient.profile.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.DialogConfirmUpdateReligionBinding
import mnshat.dev.myproject.databinding.FragmentEditProfileBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.util.RELIGION
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.loadImage

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel:ProfileViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferencesManager
   private  var canCheck = true
    private var imageUri: Uri? = null
   private  var isPicked = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        sharedPreferences = viewModel.sharedPreferences
        initializeViews()
        setupClickListener()
        return binding.root
    }

    private fun initializeViews() {
        if (viewModel.userProfile.religion!!) {
            binding.metadata.yes.isChecked = true
        }
        else {
            binding.metadata.no.isChecked = true
        }

        loadImage(requireActivity(),viewModel.userProfile.imageUser,binding.imageUser)

        binding.metadata.textName.text = viewModel.userProfile.name
        binding.metadata.textAge.text = getTextAge(viewModel.userProfile.ageGroup)
        binding.metadata.textGender.text = getTextGender(viewModel.userProfile.gender)
    }
    private fun setupClickListener() {

        binding.imageUser.setOnClickListener{
            pickImageFromGallery()
        }

        binding.updateImage.setOnClickListener {
            if (isPicked){
                checkInternetConnection()
            }else{
                pickImageFromGallery()
            }
        }

        binding.metadata.groupRoot.setOnCheckedChangeListener { group, checkedId ->
            if (canCheck){
                when (checkedId) {
                    R.id.yes -> {
                        showDialog(RELIGION,true)
                    }
                    R.id.no -> {
                        showDialog(RELIGION,false)
                    }
                }
            }
        }


        binding.icBack.setOnClickListener {
            activity?.finish()
        }
        binding.metadata.name.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_changeUserNameFragment)
        }
        binding.metadata.pass.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_changePassFragment)
        }
        binding.metadata.age.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_editAgeFragment)
        }
        binding.metadata.gender.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_editGenderFragment)
        }
    }
    private fun checkInternetConnection() {
        if (isConnected()) {
            showProgressDialog()
            viewModel.uploadImageToFireStorage(imageUri!!)

        } else {
            showNoInternetSnackBar(binding.root)
        }
    }




    private fun editReligion(key: String, needReligion: Boolean) {
        if (needReligion != sharedPreferences.getBoolean(RELIGION)){
        showProgressDialog()
        val map = mapOf<String, Any>(key to needReligion)
        FirebaseService.updateItemsProfileUser(FirebaseService.userId, map) {
            if (it) {
                showToast("تم تحديث الحالة الدينية")
                sharedPreferences.storeBoolean(key, needReligion!!)
                viewModel.resetCurrentDay()

            } else {
                showToast(getString(R.string.update_failed))
            }
            dismissProgressDialog()
        }
    }
    }

    fun getTextAge( age: Int?):String? {
        return  when(age){
            1 ->  getString(R.string.young_adulthood)
            2 ->  getString(R.string.middle_age)
            3 ->  getString(R.string.older)
            else -> null
        }

    }

    fun getTextGender(gender: Int?) :String? {
        return  when(gender){
            1 -> getString(R.string.male)
            2 -> getString(R.string.female)
            else -> null
        }

    }

    fun showDialog(key: String, needReligion: Boolean) {
        sharedDialog = Dialog(requireContext())
        sharedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogConfirmUpdateReligionBinding.inflate(layoutInflater)
        sharedDialog.setContentView(dialogBinding.root)
        sharedDialog.setCanceledOnTouchOutside(true)

        val window = sharedDialog.window
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }
        dialogBinding.icClose.setOnClickListener {
            sharedDialog.dismiss()
            resetChecked(needReligion)
        }
        dialogBinding.btnOk.setOnClickListener {
            sharedDialog.dismiss()
            editReligion(RELIGION,needReligion)
        }
        dialogBinding.btnCancel.setOnClickListener {
            sharedDialog.dismiss()
            resetChecked(needReligion)
        }
        sharedDialog.show()
    }


    private fun resetChecked(boolean: Boolean){
        canCheck = false
        if (boolean){
            binding.metadata.no.isChecked = true
        }else{
            binding.metadata.yes.isChecked = true
        }
        canCheck = true
    }


    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            binding.imageUser.setImageURI(it)
            binding.updateImage.text = getString(R.string.save)
            isPicked = true
                }
    }

    private fun pickImageFromGallery() {
        pickImageLauncher.launch("image/*")
    }





}
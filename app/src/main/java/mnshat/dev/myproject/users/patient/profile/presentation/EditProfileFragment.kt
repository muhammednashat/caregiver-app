package mnshat.dev.myproject.users.patient.profile.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.DialogConfirmUpdateReligionBinding
import mnshat.dev.myproject.databinding.FragmentEditProfileBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.GENDER
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.RELIGION
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USER_IMAGE
import mnshat.dev.myproject.util.USER_NAME
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log
import java.util.UUID

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel:ProfileViewModel by viewModels()
    private var currentLang = ""
    private lateinit var sharedPreferences: SharedPreferencesManager
   private  var canChecked = true
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

     fun initializeViews() {
         currentLang = viewModel.sharedPreferences.getString(LANGUAGE)
//        if (currentLang != ENGLISH_KEY) {
//            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
//        }

        if (sharedPreferences.getBoolean(RELIGION)) {
            binding.metadata.yes.isChecked = true
        }
        else {
            binding.metadata.no.isChecked = true
        }

        loadImage(requireActivity(),sharedPreferences.getString(USER_IMAGE),binding.imageUser)
        binding.metadata.textName.text = sharedPreferences.getString(USER_NAME)
        binding.metadata.textAge.text = getTextAge(sharedPreferences.getInt(AGE_GROUP))
        binding.metadata.textGender.text = getTextGender(sharedPreferences.getInt(GENDER))
    }



    private fun setupClickListener() {

        binding.updateImage.setOnClickListener {
            if (isPicked){
                uploadImageToFireStorage(imageUri!!)
            }else{
                pickImageFromGallery()
            }
        }

        binding.metadata.groupRoot.setOnCheckedChangeListener { group, checkedId ->
            if (canChecked){
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
        canChecked = false
        if (boolean){
            binding.metadata.no.isChecked = true
        }else{
            binding.metadata.yes.isChecked = true
        }
        canChecked = true
    }


    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            binding.imageUser.setImageURI(it)
            binding.updateImage.text = getString(R.string.save)
            isPicked = true
                }
    }

    // Pick Image from Gallery
    private fun pickImageFromGallery() {
        pickImageLauncher.launch("image/*")
    }
    // Upload Image to Firebase Storage
    private fun uploadImageToFireStorage(imageUri: Uri) {
        log(imageUri.toString())
//        val storageRef = FirebaseStorage.getInstance().reference.child("images/${UUID.randomUUID()}.jpg")
//
//        storageRef.putFile(imageUri)
//            .addOnSuccessListener {
//                storageRef.downloadUrl.addOnSuccessListener { uri ->
//                    saveImageUrlToFirestore(uri.toString()) // Save URL to Firestore
//                }
//            }
//            .addOnFailureListener {
////                Toast.makeText(this, "Upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
//            }

    }

    // Save Image URL to Firestore
    private fun saveImageUrlToFirestore(imageUrl: String) {
        val db = FirebaseFirestore.getInstance()
        val imageMap = hashMapOf("imageUrl" to imageUrl)

        db.collection("images").add(imageMap)
            .addOnSuccessListener {
//                Toast.makeText(re, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
//                Toast.makeText(this, "Failed to save image URL", Toast.LENGTH_SHORT).show()
            }
    }

}
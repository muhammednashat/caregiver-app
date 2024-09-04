package mnshat.dev.myproject.users.patient.libraraycontent

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseActivity
import mnshat.dev.myproject.databinding.ActivityEditProfileBinding
import mnshat.dev.myproject.databinding.ActivityLibraryBinding

class LibraryActivity : BaseActivity<ActivityLibraryBinding>(){
    override fun getLayout(): ActivityLibraryBinding {
        return ActivityLibraryBinding.inflate(layoutInflater)

    }




}
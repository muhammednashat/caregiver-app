package mnshat.dev.myproject.features.chatting

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseActivity
import mnshat.dev.myproject.databinding.ActivityChatingBinding
import mnshat.dev.myproject.databinding.ActivityLibraryBinding

class ChattingActivity : BaseActivity<ActivityChatingBinding>(){
    override fun getLayout(): ActivityChatingBinding {
        return ActivityChatingBinding.inflate(layoutInflater)

    }

}
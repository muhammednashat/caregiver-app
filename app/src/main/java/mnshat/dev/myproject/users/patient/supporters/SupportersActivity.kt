package mnshat.dev.myproject.users.patient.supporters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R

@AndroidEntryPoint
class SupportersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supporters)

    }
}
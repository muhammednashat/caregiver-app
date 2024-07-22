package mnshat.dev.myproject.users.patient.supporters

import mnshat.dev.myproject.base.BaseActivity
import mnshat.dev.myproject.databinding.ActivitySupportersBinding

class SupportersActivity : BaseActivity<ActivitySupportersBinding>() {
    override fun getLayout(): ActivitySupportersBinding {
     return ActivitySupportersBinding.inflate(layoutInflater)

    }

    override fun initializeViews() {
    }


}
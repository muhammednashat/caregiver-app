package mnshat.dev.myproject.chatting.presintation

import mnshat.dev.myproject.base.BaseActivity
import mnshat.dev.myproject.databinding.ActivityChatingBinding

class ChatActivity : BaseActivity<ActivityChatingBinding>(){
    override fun getLayout(): ActivityChatingBinding {
        return ActivityChatingBinding.inflate(layoutInflater)

    }

}
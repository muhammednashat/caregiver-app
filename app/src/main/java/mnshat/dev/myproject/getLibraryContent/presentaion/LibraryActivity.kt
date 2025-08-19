package mnshat.dev.myproject.getLibraryContent.presentaion

import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.base.BaseActivity
import mnshat.dev.myproject.databinding.ActivityLibraryBinding
@AndroidEntryPoint
class LibraryActivity : BaseActivity<ActivityLibraryBinding>() {
    override fun getLayout(): ActivityLibraryBinding {
        return ActivityLibraryBinding.inflate(layoutInflater)

    }

}
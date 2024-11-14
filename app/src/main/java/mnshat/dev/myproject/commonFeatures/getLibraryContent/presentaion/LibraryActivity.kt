package mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion

import mnshat.dev.myproject.base.BaseActivity
import mnshat.dev.myproject.databinding.ActivityLibraryBinding

class LibraryActivity : BaseActivity<ActivityLibraryBinding>() {
    override fun getLayout(): ActivityLibraryBinding {
        return ActivityLibraryBinding.inflate(layoutInflater)

    }

}
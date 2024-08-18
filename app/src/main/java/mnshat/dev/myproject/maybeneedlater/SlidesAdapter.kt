package mnshat.dev.myproject.maybeneedlater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import mnshat.dev.myproject.users.patient.dailyprogram.BehaviouralActivationFragment
import mnshat.dev.myproject.users.patient.dailyprogram.SpiritualFragment
import mnshat.dev.myproject.users.patient.dailyprogram.EducationalFragment

class SlidesAdapter(fragmentActivity: FragmentActivity) :FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return BehaviouralActivationFragment()
            1 -> return EducationalFragment()
            2 -> return SpiritualFragment()
            3 -> return SpiritualFragment()
            4 -> return GratitudeFragment()
        }
        return BehaviouralActivationFragment()
    }
}

package mnshat.dev.myproject.maybeneedlater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import mnshat.dev.myproject.users.patient.dailyprogram.ActivityFragment
import mnshat.dev.myproject.users.patient.dailyprogram.BehaviorOrSpiritualFragment
import mnshat.dev.myproject.users.patient.dailyprogram.ContemplationFragment
import mnshat.dev.myproject.users.patient.dailyprogram.GratitudeFragment

class SlidesAdapter(fragmentActivity: FragmentActivity) :FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ActivityFragment()
            1 -> return ContemplationFragment()
            2 -> return BehaviorOrSpiritualFragment()
            3 -> return SpiritualFragment()
            4 -> return GratitudeFragment()
        }
        return ActivityFragment()
    }
}

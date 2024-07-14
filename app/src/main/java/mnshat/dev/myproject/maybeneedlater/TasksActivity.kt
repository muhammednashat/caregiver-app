package mnshat.dev.myproject.maybeneedlater

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import mnshat.dev.myproject.R


class TasksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val btnPrevious = findViewById<View>(R.id.btn_previous)
        val btnNext = findViewById<View>(R.id.btn_next)

        viewPager.adapter = SlidesAdapter(this)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val adapter = viewPager.adapter
                val itemCount = adapter?.itemCount ?: 0

                btnPrevious.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
                btnNext.visibility = if (position == itemCount - 1) View.INVISIBLE else View.VISIBLE
            }
        })

        btnPrevious.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem > 0) {
                viewPager.currentItem = currentItem - 1
            }
        }

        btnNext.setOnClickListener {
            val currentItem = viewPager.currentItem
            val adapter = viewPager.adapter
            if (adapter != null && currentItem < adapter.itemCount - 1) {
                viewPager.currentItem = currentItem + 1
            }
        }
    }
}
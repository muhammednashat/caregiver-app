package mnshat.dev.myproject.users.patient.main

import android.app.Dialog
import android.os.Bundle
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding
import com.google.gson.Gson
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.model.CurrentTask
import mnshat.dev.myproject.util.CURRENT_TASK

open abstract class BaseUserFragment<T : ViewDataBinding> : BaseFragment<T>() {

    lateinit var sharedUserDialog: Dialog

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun getCurrentTask(): CurrentTask? {
        val string = sharedPreferences.getString(CURRENT_TASK, null.toString())
        val  gson = Gson()
        return gson.fromJson(string, CurrentTask::class.java)
    }

    fun changeColorOfTaskImage(status: Int?, root: ConstraintLayout, image: ImageView){
        when(status){
            1 -> {
                image.setImageResource( R.drawable.ic_check_blue)
                root.setBackgroundResource(R.drawable.circle_blue_dark)
            }
            2 -> {
                val params = root.layoutParams
                params.width = 70
                params.height = 70
                root.layoutParams = params
                root.setBackgroundResource(R.drawable.circle_orange_with_border)
            }
        }
    }


}
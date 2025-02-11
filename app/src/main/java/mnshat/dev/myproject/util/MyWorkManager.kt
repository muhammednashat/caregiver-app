package mnshat.dev.myproject.util

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorkManager (context: Context,workerParams:WorkerParameters):Worker(context,workerParams){

    override fun doWork(): Result {
   return Result.success()
    }
}
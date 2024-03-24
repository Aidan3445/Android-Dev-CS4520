package com.cs4520.assignment5.workManager

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit.MINUTES

class ProductWorkScheduler {
    fun scheduleProductWork(context: Context) {
        val workRequest =
            PeriodicWorkRequestBuilder<ProductRefreshWorker>(
                15,
                MINUTES,
                5,
                MINUTES,
            )
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build(),
                )
                .build()

        // enqueue work
        WorkManager.getInstance(context).enqueue(workRequest)

        Log.d("Worker", "Worker scheduled")
    }
}

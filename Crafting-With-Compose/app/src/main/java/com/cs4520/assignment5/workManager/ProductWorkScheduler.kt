package com.cs4520.assignment5.workManager

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit.HOURS
import java.util.concurrent.TimeUnit.MINUTES

class ProductWorkScheduler {
    fun scheduleProductWork(context: Context) {
        val workRequest =
            PeriodicWorkRequestBuilder<ProductRefreshWorker>(
                1,
                HOURS,
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
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "ProductRefreshWorker",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            workRequest,
        )

        Log.d("Worker", "Worker scheduled")
    }
}

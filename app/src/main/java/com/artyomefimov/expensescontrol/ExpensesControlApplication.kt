package com.artyomefimov.expensescontrol

import android.app.Application
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.artyomefimov.expensescontrol.infrastructure.CheckTodayExpensesWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ExpensesControlApplication : Application() {

    @Inject
    lateinit var configuration: Configuration

    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(this, configuration)
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                CheckTodayExpensesWorker.TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                CheckTodayExpensesWorker.buildWorkRequest()
            )
    }
}
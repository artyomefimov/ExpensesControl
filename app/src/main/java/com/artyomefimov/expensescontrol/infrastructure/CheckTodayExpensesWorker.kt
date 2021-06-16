package com.artyomefimov.expensescontrol.infrastructure

import android.content.Context
import androidx.work.*
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.interactor.dailyexpense.ExpenseInteractor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import java.util.concurrent.TimeUnit

class CheckTodayExpensesWorker(
    context: Context,
    parameters: WorkerParameters,
    private val expenseInteractor: ExpenseInteractor,
    private val notificationBuilder: NotificationBuilder,
    private val clock: Clock,
) : CoroutineWorker(context, parameters) {

    companion object {
        private const val SCHEDULE_HOURS = 12L
        const val TAG = "CheckTodayExpensesWorker"
        const val IS_SHOWN_KEY = "IS_SHOWN"
        const val TIMESTAMP_KEY = "TIMESTAMP"

        fun buildWorkRequest(): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<CheckTodayExpensesWorker>(
                SCHEDULE_HOURS,
                TimeUnit.HOURS
            ).build()
        }
    }

    override suspend fun doWork(): Result {
        var isShown = false
        val expenses = expenseInteractor.getExpensesForCurrentDay().first()
        if (expenses.isEmpty()) {
            showNotification()
            isShown = true
        }
        val output = workDataOf(
            IS_SHOWN_KEY to isShown,
            TIMESTAMP_KEY to clock.now().toString()
        )
        return Result.success(output)
    }

    private fun showNotification() {
        notificationBuilder.showNotification(
            titleResId = R.string.notification_title,
            contentResId = R.string.notification_text,
            iconResId = R.drawable.ic_launcher_foreground,
        )
    }
}
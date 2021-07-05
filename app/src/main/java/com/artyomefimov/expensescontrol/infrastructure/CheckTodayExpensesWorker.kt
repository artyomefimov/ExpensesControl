package com.artyomefimov.expensescontrol.infrastructure

import android.content.Context
import androidx.work.*
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.interactor.expense.ExpenseInteractor
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

/**
 * [Worker], раз в 12 часов проверяющий, были ли добавлены траты за текущий день.
 * Если таких трат не найдено, показывает уведомление пользователю с напоминанием
 */
class CheckTodayExpensesWorker(
    context: Context,
    parameters: WorkerParameters,
    private val expenseInteractor: ExpenseInteractor,
    private val notificationBuilder: NotificationBuilder,
) : CoroutineWorker(context, parameters) {

    companion object {
        private const val SCHEDULE_HOURS = 12L
        const val TAG = "CheckTodayExpensesWorker"

        fun buildWorkRequest(): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<CheckTodayExpensesWorker>(
                SCHEDULE_HOURS,
                TimeUnit.HOURS
            ).build()
        }
    }

    override suspend fun doWork(): Result {
        val expenses = expenseInteractor.getExpensesForCurrentDay().first()
        if (expenses.isEmpty()) {
            showNotification()
        }
        return Result.success()
    }

    private fun showNotification() {
        notificationBuilder.showNotification(
            titleResId = R.string.notification_title,
            contentResId = R.string.notification_text,
            iconResId = R.drawable.ic_expenses,
        )
    }
}

package com.artyomefimov.expensescontrol.infrastructure

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.work.*
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.domain.interactor.expense.ExpenseInteractor
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import java.util.concurrent.TimeUnit

/**
 * [Worker], раз в 12 часов проверяющий, были ли добавлены траты за текущий день.
 * Если таких трат не найдено, показывает уведомление пользователю с напоминанием
 */
class CheckTodayExpensesWorker(
    parameters: WorkerParameters,
    context: Context,
    private val expenseInteractor: ExpenseInteractor,
    private val notificationManager: NotificationManager,
    private val clock: Clock,
    private val dataStore: DataStore<Preferences>,
) : CoroutineWorker(context, parameters) {

    companion object {
        private const val SCHEDULE_HOURS = 12L
        private val LAST_SHOWN_NOTIFICATION = stringPreferencesKey(
            "LAST_SHOWN_NOTIFICATION"
        )
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
        if (expenses.isEmpty() && notificationManager.hasNoActiveNotifications()) {
            showNotification()
            dataStore.edit { prefs ->
                prefs[LAST_SHOWN_NOTIFICATION] = clock.now().toString()
            }
        }
        return Result.success()
    }

    private fun showNotification() {
        notificationManager.showNotification(
            titleResId = R.string.notification_title,
            contentResId = R.string.notification_text,
            iconResId = R.drawable.ic_expenses,
        )
    }
}

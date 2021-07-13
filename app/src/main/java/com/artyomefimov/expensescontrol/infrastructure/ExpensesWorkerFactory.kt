package com.artyomefimov.expensescontrol.infrastructure

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.artyomefimov.expensescontrol.domain.interactor.expense.ExpenseInteractor
import kotlinx.datetime.Clock
import javax.inject.Inject

class ExpensesWorkerFactory @Inject constructor(
    private val expenseInteractor: ExpenseInteractor,
    private val notificationBuilder: NotificationBuilder,
    private val clock: Clock,
    private val dataStore: DataStore<Preferences>,
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return CheckTodayExpensesWorker(
            workerParameters,
            appContext,
            expenseInteractor,
            notificationBuilder,
            clock,
            dataStore
        )
    }
}

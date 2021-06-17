package com.artyomefimov.expensescontrol.infrastructure

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.artyomefimov.expensescontrol.domain.interactor.dailyexpense.ExpenseInteractor
import kotlinx.datetime.Clock
import javax.inject.Inject

class ExpensesWorkerFactory @Inject constructor(
    private val expenseInteractor: ExpenseInteractor,
    private val notificationBuilder: NotificationBuilder,
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return CheckTodayExpensesWorker(
            appContext,
            workerParameters,
            expenseInteractor,
            notificationBuilder,
        )
    }
}

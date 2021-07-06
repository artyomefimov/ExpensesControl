package com.artyomefimov.expensescontrol.infrastructure.widget

import android.content.Intent
import android.widget.RemoteViewsService
import com.artyomefimov.expensescontrol.domain.interactor.expense.ExpenseInteractor
import com.artyomefimov.expensescontrol.domain.mapper.Mapper
import com.artyomefimov.expensescontrol.domain.mapper.mapList
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.presentation.model.ExpenseInfo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.Clock
import java.math.BigDecimal
import javax.inject.Inject

class ExpensesWidgetRemoteViewsService: RemoteViewsService() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private var expenses: List<ExpenseInfo> = emptyList()

//    @Inject
//    lateinit var expenseInteractor: ExpenseInteractor
//    @Inject
//    lateinit var expenseInfoMapper: Mapper<Expense, ExpenseInfo>

    override fun onCreate() {
        super.onCreate()
        // todo di
        val expense1 = ExpenseInfo(
            id = 0,
            sum = BigDecimal.ZERO.toString(),
            comment = "",
            category = "Развлечения",
            timestamp = Clock.System.now().toString()
        )
        val expense2 = ExpenseInfo(
            id = 1,
            sum = BigDecimal.ONE.toString(),
            comment = "",
            category = "Развлечения",
            timestamp = Clock.System.now().toString()
        )
        val expense3 = ExpenseInfo(
            id = 2,
            sum = BigDecimal.TEN.toString(),
            comment = "",
            category = "Обязательные",
            timestamp = Clock.System.now().toString()
        )
        scope.launch {
//            expenseInteractor.getExpensesForCurrentMonth().collectLatest {
//                expenses = it.mapList(expenseInfoMapper)
//            }
            expenses = listOf(expense1, expense2, expense3)
        }
    }

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return ExpensesWidgetRemoteViewsFactory(applicationContext, expenses)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
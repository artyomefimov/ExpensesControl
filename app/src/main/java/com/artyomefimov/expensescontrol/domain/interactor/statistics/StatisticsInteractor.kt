package com.artyomefimov.expensescontrol.domain.interactor.statistics

import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.domain.model.statistics.StatisticsFilter
import kotlinx.coroutines.flow.StateFlow

interface StatisticsInteractor {

    suspend fun applyFilter(filter: StatisticsFilter)

    fun getExpensesFlow(): StateFlow<List<Expense>>
}
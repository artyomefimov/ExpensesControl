package com.artyomefimov.expensescontrol.domain.interactor.statistics

import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.domain.model.statistics.chart.ChartData
import kotlinx.coroutines.flow.StateFlow

interface ChartInteractor {

    suspend fun prepareDataForChart(expenses: List<Expense>)

    fun getChartData(): StateFlow<ChartData>
}

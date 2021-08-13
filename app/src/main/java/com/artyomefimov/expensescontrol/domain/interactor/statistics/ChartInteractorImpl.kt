package com.artyomefimov.expensescontrol.domain.interactor.statistics

import com.artyomefimov.expensescontrol.di.DefaultDispatcher
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.domain.model.statistics.chart.ChartData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class ChartInteractorImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : ChartInteractor {

    private companion object {
        val ONE_HUNDRED = BigDecimal("100")
    }

    private val chartDataFlow = MutableSharedFlow<ChartData>()

    override suspend fun prepareDataForChart(
        expenses: List<Expense>
    ) = withContext(defaultDispatcher) {
        val chartData = percentsOfCommonSumGroupedByCategory(
            commonSum = expenses.sumOf { it.sum },
            expensesByCategory = expenses.groupBy { it.category },
        )
        chartDataFlow.emit(chartData)
    }

    override fun getChartData(): SharedFlow<ChartData> = chartDataFlow

    private fun percentsOfCommonSumGroupedByCategory(
        commonSum: BigDecimal,
        expensesByCategory: Map<String, List<Expense>>,
    ): ChartData {
        val categoryAndSum = mutableMapOf<String, BigDecimal>()
        expensesByCategory.forEach { (category, expenses) ->
            val categoryCommonSum = expenses.sumOf { it.sum }
            val percent = categoryCommonSum.divide(
                commonSum,
                2,
                RoundingMode.HALF_UP,
            ).multiply(ONE_HUNDRED)
            if (percent >= BigDecimal.ONE) {
                categoryAndSum[category] = percent
            }
        }
        return ChartData(data = categoryAndSum)
    }
}

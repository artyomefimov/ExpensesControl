package com.artyomefimov.expensescontrol.domain.interactor.statistics

import com.artyomefimov.expensescontrol.di.DefaultDispatcher
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.domain.model.statistics.chart.ChartData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

// todo добавить тест
class ChartInteractorImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : ChartInteractor {

    private companion object {
        val ONE_HUNDRED = BigDecimal("100.00")
    }

    private val chartDataFlow = MutableStateFlow(ChartData())

    override suspend fun prepareDataForChart(
        expenses: List<Expense>
    ) = withContext(defaultDispatcher) {
        chartDataFlow.value = percentsOfCommonSumGroupedByCategory(
            commonSum = expenses.sumOf { it.sum },
            expensesByCategory = expenses.groupBy { it.category },
        )
    }

    override fun getChartData(): StateFlow<ChartData> = chartDataFlow

    private fun percentsOfCommonSumGroupedByCategory(
        commonSum: BigDecimal,
        expensesByCategory: Map<String, List<Expense>>,
    ): ChartData {
        val categoryAndSum = mutableMapOf<String, BigDecimal>()
        expensesByCategory.forEach { (category, expenses) ->
            val categoryCommonSum = expenses.sumOf { it.sum }
            val percent = categoryCommonSum.div(commonSum).multiply(ONE_HUNDRED)
            categoryAndSum[category] = percent
        }
        return ChartData(data = categoryAndSum)
    }
}

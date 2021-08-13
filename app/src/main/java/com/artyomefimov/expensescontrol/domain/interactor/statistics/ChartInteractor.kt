package com.artyomefimov.expensescontrol.domain.interactor.statistics

import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.domain.model.statistics.chart.ChartData
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Содержит бизнес-логику подготовки данных для графика с информацией
 * о произведенных тратах
 */
interface ChartInteractor {

    /**
     * Производит расчет данных для графика на основе переданных трат
     *
     * @param expenses произведенные траты
     */
    suspend fun prepareDataForChart(expenses: List<Expense>)

    /**
     * Flow с актуальными данными для показа графика
     */
    fun getChartData(): SharedFlow<ChartData>
}

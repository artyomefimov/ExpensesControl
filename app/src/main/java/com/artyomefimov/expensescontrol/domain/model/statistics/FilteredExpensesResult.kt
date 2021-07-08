package com.artyomefimov.expensescontrol.domain.model.statistics

import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import java.math.BigDecimal

/**
 * Результат фильтрации текущих расходов
 *
 * @param expenses         список трат, удовлетворяющих текущий фильтр
 * @param commonSum        общая сумма отфильтрованных трат
 * @param isChartAvailable доступен ли график для просмотра трат по категориям за
 * выбранный период
 */
data class FilteredExpensesResult(
    val expenses: List<Expense> = listOf(),
    val commonSum: BigDecimal? = null,
    val isChartAvailable: Boolean = false
)

package com.artyomefimov.expensescontrol.domain.model.statistics

/**
 * Сущность, представляющая собой фильтр, по которому будут фильтроваться текущие траты
 *
 * @param periodFilter          фильтр по периоду, в течение которого была совершена трата
 * @param categoryFilter        фильтр по категории трат
 * @param isMaxSumFilterEnabled фильтр по максимальной сумме трат
 */
data class StatisticsFilter(
    val periodFilter: PeriodFilter? = null,
    val categoryFilter: String? = null,
    val isMaxSumFilterEnabled: Boolean = false,
)
package com.artyomefimov.expensescontrol.domain.model.statistics

data class StatisticsFilter(
    val periodFilter: PeriodFilter? = null,
    val categoryFilter: String? = null,
    val isMaxSumFilterEnabled: Boolean = false,
)
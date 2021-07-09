package com.artyomefimov.expensescontrol.domain.model.statistics.chart

import java.math.BigDecimal

data class ChartData(
    val data: Map<String, BigDecimal> = mapOf()
)

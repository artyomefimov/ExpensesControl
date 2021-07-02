package com.artyomefimov.expensescontrol.domain.model.statistics

import kotlinx.datetime.Instant

data class PeriodFilter(
    val from: Instant,
    val to: Instant,
)

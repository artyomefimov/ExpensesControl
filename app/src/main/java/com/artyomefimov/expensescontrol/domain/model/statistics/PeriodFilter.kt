package com.artyomefimov.expensescontrol.domain.model.statistics

import kotlinx.datetime.Instant

/**
 * Фильтр по периоду, в течение которого были совершены траты
 *
 * @param from начало периода
 * @param to   конец периода
 */
data class PeriodFilter(
    val from: Instant,
    val to: Instant,
)

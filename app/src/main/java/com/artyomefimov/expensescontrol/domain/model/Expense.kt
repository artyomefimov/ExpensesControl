package com.artyomefimov.expensescontrol.domain.model

import kotlinx.datetime.Instant
import java.math.BigDecimal

/**
 * Трата денег, произведенная пользователем
 *
 * @param sum       сумма траты
 * @param comment   комментарий к трате
 * @param category  категория траты
 * @param timestamp время, когда была зафиксирована трата
 */
data class Expense(
    val sum: BigDecimal,
    val comment: String,
    val category: String,
    val timestamp: Instant,
)

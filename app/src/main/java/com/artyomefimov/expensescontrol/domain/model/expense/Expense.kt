package com.artyomefimov.expensescontrol.domain.model.expense

import kotlinx.datetime.Instant
import java.math.BigDecimal

/**
 * Трата денег, произведенная пользователем
 *
 * @param id        id траты
 * @param sum       сумма траты
 * @param comment   комментарий к трате
 * @param category  категория траты
 * @param timestamp время, когда была зафиксирована трата
 */
data class Expense(
    val id: Long = 0,
    val sum: BigDecimal,
    val comment: String,
    val category: String,
    val timestamp: Instant,
)

package com.artyomefimov.expensescontrol.presentation.model

/**
 * Представление траты, совершенной пользователем для presentation слоя
 *
 * @param id        id траты
 * @param sum       сумма траты
 * @param comment   комментарий к трате
 * @param category  категория траты
 * @param timestamp время, когда была зафиксирована трата
 */
data class ExpenseInfo(
    val id: Long,
    val sum: String,
    val comment: String,
    val category: String,
    val timestamp: String,
)

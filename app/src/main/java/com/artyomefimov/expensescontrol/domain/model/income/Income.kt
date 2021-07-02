package com.artyomefimov.expensescontrol.domain.model.income

import java.math.BigDecimal

/**
 * Сумма трат, доступная пользователю на месяц
 *
 * @param value             сумма
 * @param shouldEnterIncome нужно ли ввести новую сумму
 */
data class Income(
    val value: BigDecimal,
    val shouldEnterIncome: Boolean
)

/**
 * Возвращает true, если сумма трат равна 0 и нужно ввести новую сумму трат
 */
fun Income.isZeroAndShouldBeEntered(): Boolean {
    return this.value == BigDecimal.ZERO && this.shouldEnterIncome
}

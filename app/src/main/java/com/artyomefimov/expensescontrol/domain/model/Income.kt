package com.artyomefimov.expensescontrol.domain.model

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

fun Income.isZeroAndShouldBeEntered(): Boolean {
    return this.value == BigDecimal.ZERO && this.shouldEnterIncome
}
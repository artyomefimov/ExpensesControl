package com.artyomefimov.expensescontrol.domain.model

import java.math.BigDecimal

data class Income(
    val value: BigDecimal,
    val shouldEnterIncome: Boolean
)

fun Income.isZeroAndShouldBeEntered(): Boolean {
    return this.value == BigDecimal.ZERO && this.shouldEnterIncome
}
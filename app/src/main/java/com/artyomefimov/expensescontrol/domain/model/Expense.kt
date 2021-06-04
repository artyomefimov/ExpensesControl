package com.artyomefimov.expensescontrol.domain.model

import kotlinx.datetime.Instant
import java.math.BigDecimal

data class Expense(
    val sum: BigDecimal,
    val comment: String,
    val timestamp: Instant,
//    val categories: List<Category>
)

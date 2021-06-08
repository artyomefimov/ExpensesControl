package com.artyomefimov.expensescontrol.presentation.model

data class ExpenseInfo(
    val id: Long,
    val sum: String,
    val comment: String,
    val category: String,
    val timestamp: String,
)

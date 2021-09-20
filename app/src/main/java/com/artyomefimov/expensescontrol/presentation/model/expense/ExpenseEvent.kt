package com.artyomefimov.expensescontrol.presentation.model.expense

sealed class ExpenseEvent {

    data class MessageEvent(
        val message: String,
    ) : ExpenseEvent()
}

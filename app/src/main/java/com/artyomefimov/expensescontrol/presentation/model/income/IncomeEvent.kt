package com.artyomefimov.expensescontrol.presentation.model.income

sealed class IncomeEvent {

    object NavigateToExpense : IncomeEvent()
    data class ShowIncorrectSumMessage(
        val message: String,
    ): IncomeEvent()
}

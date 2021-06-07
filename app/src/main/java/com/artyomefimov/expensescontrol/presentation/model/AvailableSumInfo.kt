package com.artyomefimov.expensescontrol.presentation.model

data class AvailableSumInfo(
    val availableDailySum: String,
    val availableMonthlySum: String,
    val isInitial: Boolean,
)
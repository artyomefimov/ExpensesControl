package com.artyomefimov.expensescontrol.presentation.model

data class FilterModel(
    val periodFilterEnabled: Boolean,
    val categoryFilterEnabled: Boolean,
    val maxSumFilterEnabled: Boolean,
)
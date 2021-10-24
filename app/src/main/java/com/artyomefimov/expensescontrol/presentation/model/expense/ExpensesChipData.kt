package com.artyomefimov.expensescontrol.presentation.model.expense

import com.artyomefimov.expensescontrol.presentation.model.ChipData

/**
 * Содержит информацию для отображения чипов на экране добавления трат
 */
data class ExpensesChipData(
    override var title: String,
): ChipData

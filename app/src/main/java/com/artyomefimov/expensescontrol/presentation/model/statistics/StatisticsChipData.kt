package com.artyomefimov.expensescontrol.presentation.model.statistics

import com.artyomefimov.expensescontrol.presentation.model.ChipData

/**
 * Содержит информацию для отображения чипов на экране статистики
 */
data class StatisticsChipData(
    override var title: String,
    val type: FilterType,
): ChipData

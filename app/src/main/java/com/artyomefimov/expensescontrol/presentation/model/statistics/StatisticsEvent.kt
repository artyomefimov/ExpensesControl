package com.artyomefimov.expensescontrol.presentation.model.statistics

sealed class StatisticsEvent {

    object OpenPeriodDialog : StatisticsEvent()
    object OpenChartScreen : StatisticsEvent()
    class OpenCategoryDialog(
        val categories: Array<String>,
    ) : StatisticsEvent()
}

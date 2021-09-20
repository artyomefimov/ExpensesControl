package com.artyomefimov.expensescontrol.presentation.model.expense

/**
 * Информация о доступной сумме для пользователя
 *
 * @param availableDailySum   сумма, доступная на текущий день
 * @param availableMonthlySum сумма, доступная на текущий месяц
 * @param isInitial           показывает первое присвоение значений сумм
 */
data class AvailableSumInfo(
    val availableDailySum: String = "",
    val availableMonthlySum: String = "",
    val isInitial: Boolean = false,
)

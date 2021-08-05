package com.artyomefimov.expensescontrol.domain.interactor.date

import kotlinx.datetime.Instant

/**
 * Интерактор для работы с датами
 */
interface DateInteractor {

    /**
     * Возвращает [ClosedRange] между началом дня instantFrom и концом дня instantTo
     */
    fun daysRange(
        instantFrom: Instant,
        instantTo: Instant,
    ): ClosedRange<Instant>

    fun availableDaysInThisMonth(): Int

    fun isFirstDayOfMonth(): Boolean

    fun getCurrentMonthNameAndLastDay(): String
}
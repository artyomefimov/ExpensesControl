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

    /**
     * Возвращает строку с последним днем месяца и названием месяца
     */
    fun getCurrentMonthNameAndLastDay(): String

    /**
     * Возвращает true, если с момента даты прошел месяц. False - иначе
     *
     * @param date дата в виде строки
     */
    fun isMonthOfCurrentDateWasEnded(date: String): Boolean
}
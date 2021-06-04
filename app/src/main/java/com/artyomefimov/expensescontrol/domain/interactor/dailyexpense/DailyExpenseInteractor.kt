package com.artyomefimov.expensescontrol.domain.interactor.dailyexpense

import java.math.BigDecimal
import java.math.BigInteger

/**
 * Содержит бизнес-логику для внесения новых трат и рассчета доступной суммы
 */
interface DailyExpenseInteractor {

    /**
     * Получает доступную сумму для трат для текущего дня
     *
     * @return [BigDecimal] доступная сумма для трат для текущего дня,
     * рассчитанная на основе остатка на месяц
     */
    fun getAvailableDailySum(): BigDecimal

    /**
     * Добавляет новую трату за текущий день
     *
     * @param stringSum     сумма траты в виде строки
     * @param comment комментарий к трате
     */
    fun addExpense(
        stringSum: String,
        comment: String,
    )
}
package com.artyomefimov.expensescontrol.domain.interactor.dailyexpense

import com.artyomefimov.expensescontrol.domain.model.Expense
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Содержит бизнес-логику для внесения новых трат и рассчета доступной суммы
 */
interface ExpenseInteractor {

    /**
     * Возвращает [Flow] списка всех трат, введенных пользователем
     */
    fun getAllExpenses(): Flow<List<Expense>>

    /**
     * Возвращает [Flow] списка всех трат за текущий месяц
     */
    fun getExpensesForCurrentMonth(): Flow<List<Expense>>

    /**
     * Возвращает [Flow] списка всех трат за текущий день
     */
    fun getExpensesForCurrentDay(): Flow<List<Expense>>

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
     * @param stringSum сумма траты в виде строки
     * @param comment   комментарий к трате
     * @param category  категория траты
     */
    suspend fun addExpense(
        stringSum: String,
        comment: String,
        category: String,
    )
}

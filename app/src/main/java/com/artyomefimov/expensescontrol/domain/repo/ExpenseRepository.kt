package com.artyomefimov.expensescontrol.domain.repo

import com.artyomefimov.expensescontrol.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {

    /**
     * Возвращает [Flow] списка всех трат, введенных пользователем
     */
    fun allExpenses(): Flow<List<Expense>>

    /**
     * Возвращает [Flow] списка всех трат за текущий месяц
     */
    fun getExpensesForCurrentMonth(): Flow<List<Expense>>

    /**
     * Возвращает [Flow] списка всех трат за текущий день
     */
    fun getExpensesForCurrentDay(): Flow<List<Expense>>

    /**
     * Добавляет новую трату за текущий день
     *
     * @param expense трата
     */
    suspend fun addExpense(expense: Expense)
}
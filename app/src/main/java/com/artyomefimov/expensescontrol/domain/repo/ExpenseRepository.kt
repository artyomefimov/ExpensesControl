package com.artyomefimov.expensescontrol.domain.repo

import com.artyomefimov.expensescontrol.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {

    fun allExpenses(): Flow<List<Expense>>

    suspend fun addExpense(expense: Expense)
}
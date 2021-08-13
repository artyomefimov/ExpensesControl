package com.artyomefimov.expensescontrol.domain.interactor.expense

import android.content.Context
import com.artyomefimov.expensescontrol.domain.ext.updateWidget
import com.artyomefimov.expensescontrol.domain.interactor.date.DateInteractor
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.domain.repo.expense.ExpenseRepository
import com.artyomefimov.expensescontrol.domain.repo.income.IncomeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class ExpenseInteractorImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository,
    private val clock: Clock,
    private val dateInteractor: DateInteractor,
) : ExpenseInteractor {

    private companion object {
        const val ROUNDING_SCALE = 0
    }

    override fun getAllExpenses(): Flow<List<Expense>> {
        return expenseRepository
            .allExpenses()
            .map(::reversed)
    }

    override fun getExpensesForCurrentMonth(): Flow<List<Expense>> {
        return expenseRepository
            .getExpensesForCurrentMonth()
            .map(::reversed)
    }

    override fun getExpensesForCurrentDay(): Flow<List<Expense>> {
        return expenseRepository
            .getExpensesForCurrentDay()
            .map(::reversed)
    }

    override suspend fun getAvailableDailySum(): BigDecimal {
        return incomeRepository.getIncomeValue()
            .divide(
                BigDecimal(dateInteractor.availableDaysInThisMonth()),
                ROUNDING_SCALE,
                RoundingMode.HALF_EVEN
            )
    }

    override suspend fun addExpense(
        stringSum: String,
        comment: String,
        category: String,
    ) {
        val expense = Expense(
            sum = BigDecimal(stringSum),
            comment = comment,
            category = category,
            timestamp = clock.now(),
        )
        val newSum = incomeRepository.getIncomeValue().subtract(expense.sum)
        incomeRepository.updateIncome(newSum)
        expenseRepository.addExpense(expense)
        context.updateWidget()
    }

    private fun reversed(expenses: List<Expense>): List<Expense> {
        return expenses.asReversed()
    }
}

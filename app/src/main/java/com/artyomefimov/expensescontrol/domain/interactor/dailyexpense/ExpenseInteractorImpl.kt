package com.artyomefimov.expensescontrol.domain.interactor.dailyexpense

import com.artyomefimov.expensescontrol.domain.ext.today
import com.artyomefimov.expensescontrol.domain.model.Expense
import com.artyomefimov.expensescontrol.domain.repo.ExpenseRepository
import com.artyomefimov.expensescontrol.domain.repo.IncomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaLocalDateTime
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class ExpenseInteractorImpl @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository,
    private val clock: Clock,
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

    override fun getAvailableDailySum(): BigDecimal {
        return incomeRepository.getIncomeValue()
            .divide(
                BigDecimal(availableDaysInThisMonth()),
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
    }

    private fun availableDaysInThisMonth(): Int {
        val today = today(clock)
        val isLeap = today.toJavaLocalDateTime().toLocalDate().isLeapYear
        return today.month.length(isLeap) - today.dayOfMonth
    }

    private fun reversed(expenses: List<Expense>): List<Expense> {
        return expenses.asReversed()
    }
}
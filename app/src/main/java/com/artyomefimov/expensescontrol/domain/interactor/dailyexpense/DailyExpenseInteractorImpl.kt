package com.artyomefimov.expensescontrol.domain.interactor.dailyexpense

import com.artyomefimov.expensescontrol.domain.ext.isInCurrentMonth
import com.artyomefimov.expensescontrol.domain.ext.today
import com.artyomefimov.expensescontrol.domain.repo.ExpenseRepository
import com.artyomefimov.expensescontrol.domain.repo.IncomeRepository
import com.artyomefimov.expensescontrol.domain.model.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaLocalDateTime
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class DailyExpenseInteractorImpl @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository,
    private val clock: Clock,
) : DailyExpenseInteractor {

    private companion object {
        const val ROUNDING_SCALE = 0
    }

    override fun getAllExpensesForCurrentMonth(): Flow<List<Expense>> {
        return expenseRepository.allExpenses().map { expenses ->
            expenses.filter { expense -> expense.timestamp.isInCurrentMonth(clock) }
        }
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
}
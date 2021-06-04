package com.artyomefimov.expensescontrol.domain.interactor.dailyexpense

import com.artyomefimov.expensescontrol.domain.ext.today
import com.artyomefimov.expensescontrol.domain.interactor.repo.IncomeRepository
import com.artyomefimov.expensescontrol.domain.model.Expense
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaLocalDateTime
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import javax.inject.Inject

class DailyExpenseInteractorImpl @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val clock: Clock,
) : DailyExpenseInteractor {

    private companion object {
        const val ROUNDING_SCALE = 0
    }

    override fun getAvailableDailySum(): BigDecimal {
        return incomeRepository.getIncomeValue()
            .divide(
                BigDecimal(daysInThisMonth()),
                ROUNDING_SCALE,
                RoundingMode.HALF_EVEN
            )
    }

    override fun addExpense(
        stringSum: String,
        comment: String,
    ) {
        val expense = Expense(
            sum = BigDecimal(stringSum),
            comment = comment,
            timestamp = Clock.System.now(),
        )
        val newSum = incomeRepository.getIncomeValue().subtract(expense.sum)
        incomeRepository.updateIncome(newSum)
    }

    private fun daysInThisMonth(): Int {
        val isLeap = today(clock).toJavaLocalDateTime().toLocalDate().isLeapYear
        return today(clock).month.length(isLeap)
    }
}
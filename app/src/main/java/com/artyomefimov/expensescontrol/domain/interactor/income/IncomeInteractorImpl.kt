package com.artyomefimov.expensescontrol.domain.interactor.income

import com.artyomefimov.expensescontrol.domain.ext.today
import com.artyomefimov.expensescontrol.domain.model.income.Income
import com.artyomefimov.expensescontrol.domain.repo.income.IncomeRepository
import kotlinx.datetime.Clock
import java.math.BigDecimal
import javax.inject.Inject

class IncomeInteractorImpl @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val clock: Clock,
): IncomeInteractor {

    private companion object {
        const val FIRST_DAY = 1
    }

    override fun getIncomeForCurrentMonth(): Income {
        return if (isFirstDayOfMonth() || hasNoChangeDate()) {
            Income(
                value = BigDecimal.ZERO,
                shouldEnterIncome = true
            )
        } else {
            Income(
                value = incomeRepository.getIncomeValue(),
                shouldEnterIncome = false
            )
        }
    }

    override fun saveIncomeForNextMonth(incomeString: String) {
        incomeRepository.updateIncome(BigDecimal(incomeString))
        incomeRepository.setLastChangeDate(clock.now())
    }

    private fun isFirstDayOfMonth(): Boolean {
        return today(clock).dayOfMonth == FIRST_DAY
    }

    private fun hasNoChangeDate(): Boolean {
        return incomeRepository.getLastChangeDateString().isEmpty()
    }
}

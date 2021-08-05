package com.artyomefimov.expensescontrol.domain.interactor.income

import com.artyomefimov.expensescontrol.domain.interactor.date.DateInteractor
import com.artyomefimov.expensescontrol.domain.model.income.Income
import com.artyomefimov.expensescontrol.domain.repo.income.IncomeRepository
import kotlinx.datetime.Clock
import java.math.BigDecimal
import javax.inject.Inject

class IncomeInteractorImpl @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val clock: Clock,
    private val dateInteractor: DateInteractor,
) : IncomeInteractor {

    override suspend fun getIncomeForCurrentMonth(): Income {
        return if (shouldEnterIncome()) {
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

    override suspend fun saveIncomeForNextMonth(incomeString: String) {
        incomeRepository.updateIncome(BigDecimal(incomeString))
        incomeRepository.setLastChangeDate(clock.now())
    }

    private suspend fun shouldEnterIncome(): Boolean {
        val hasNoChangeDate = incomeRepository.getLastChangeDateString().isEmpty()
        return dateInteractor.isFirstDayOfMonth() || hasNoChangeDate
    }
}

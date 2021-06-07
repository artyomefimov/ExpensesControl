package com.artyomefimov.expensescontrol.domain.interactor.dailyexpense

import com.artyomefimov.expensescontrol.domain.repo.ExpenseRepository
import com.artyomefimov.expensescontrol.domain.repo.IncomeRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class DailyExpenseInteractorImplTest {

    private companion object {
        val currentDate = Instant.parse("2018-08-20T10:00:00Z")
        const val daysInCurrentMonth = 31
        const val availableDays = 11
    }

    private val incomeRepository = mockk<IncomeRepository>()
    private val expenseRepository = mockk<ExpenseRepository>()
    private val clock = mockk<Clock>()
    private val interactor = DailyExpenseInteractorImpl(incomeRepository, expenseRepository, clock)

    @Test
    fun `getAvailableDailySum returns zero if monthly income is zero`() {
        every { clock.now() } returns currentDate
        every { incomeRepository.getIncomeValue() } returns BigDecimal.ZERO

        val result = interactor.getAvailableDailySum()

        assertEquals(BigDecimal.ZERO, result)
    }

    @Test
    fun `getAvailableDailySum returns average value for days left if monthly income is not zero`() {
        val expected = 1000
        val incomeValue = (availableDays * expected).toString()
        every { clock.now() } returns currentDate
        every { incomeRepository.getIncomeValue() } returns BigDecimal(incomeValue)

        val result = interactor.getAvailableDailySum()

        assertEquals(BigDecimal(expected.toString()), result)
    }
}
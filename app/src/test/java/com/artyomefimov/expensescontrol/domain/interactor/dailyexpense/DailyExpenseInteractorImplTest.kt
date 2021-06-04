package com.artyomefimov.expensescontrol.domain.interactor.dailyexpense

import com.artyomefimov.expensescontrol.domain.interactor.repo.IncomeRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class DailyExpenseInteractorImplTest {

    private companion object {
        val currentDate = Instant.parse("2018-08-01T10:00:00Z")
        const val daysInCurrentMonth = 31
    }

    private val repository = mockk<IncomeRepository>()
    private val clock = mockk<Clock>()
    private val interactor = DailyExpenseInteractorImpl(repository, clock)

    @Test
    fun `getAvailableDailySum returns zero if monthly income is zero`() {
        every { clock.now() } returns currentDate
        every { repository.getIncomeValue() } returns BigDecimal.ZERO

        val result = interactor.getAvailableDailySum()

        assertEquals(BigDecimal.ZERO, result)
    }

    @Test
    fun `getAvailableDailySum returns average value if monthly income is not zero`() {
        val expected = 1000
        val incomeValue = (daysInCurrentMonth * expected).toString()
        every { clock.now() } returns currentDate
        every { repository.getIncomeValue() } returns BigDecimal(incomeValue)

        val result = interactor.getAvailableDailySum()

        assertEquals(BigDecimal(expected.toString()), result)
    }
}
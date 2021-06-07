package com.artyomefimov.expensescontrol.domain.interactor.income

import com.artyomefimov.expensescontrol.domain.repo.IncomeRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

class IncomeInteractorImplTest {

    private companion object {
        val firstDayOfMonth = Instant.parse("2018-08-01T10:00:00Z")
        val secondDayOfMonth = Instant.parse("2018-08-02T10:00:00Z")
        const val notEmptyString = "some string"
        const val emptyString = ""
    }

    private val repository = mockk<IncomeRepository>()
    private val clock = mockk<Clock>()
    private val interactor = IncomeInteractorImpl(repository, clock)

    @Test
    fun `getIncomeForCurrentMonth returns shouldEnterIncome true for empty change date`() {
        every { clock.now() } returns secondDayOfMonth
        every { repository.getLastChangeDateString() } returns emptyString

        val income = interactor.getIncomeForCurrentMonth()

        assertEquals(BigDecimal.ZERO, income.value)
        assertTrue(income.shouldEnterIncome)
    }


    @Test
    fun `getIncomeForCurrentMonth returns shouldEnterIncome true for first day of month`() {
        every { clock.now() } returns firstDayOfMonth
        every { repository.getLastChangeDateString() } returns notEmptyString

        val income = interactor.getIncomeForCurrentMonth()

        assertEquals(BigDecimal.ZERO, income.value)
        assertTrue(income.shouldEnterIncome)
    }


    @Test
    fun `getIncomeForCurrentMonth returns shouldEnterIncome false for another cases`() {
        every { clock.now() } returns secondDayOfMonth
        every { repository.getLastChangeDateString() } returns notEmptyString
        every { repository.getIncomeValue() } returns BigDecimal.TEN

        val income = interactor.getIncomeForCurrentMonth()

        assertEquals(BigDecimal.TEN, income.value)
        assertFalse(income.shouldEnterIncome)
        verify(exactly = 1) { repository.getIncomeValue() }
    }
}
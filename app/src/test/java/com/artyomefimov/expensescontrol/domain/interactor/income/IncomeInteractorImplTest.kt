package com.artyomefimov.expensescontrol.domain.interactor.income

import com.artyomefimov.expensescontrol.domain.interactor.date.DateInteractor
import com.artyomefimov.expensescontrol.domain.repo.income.IncomeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.datetime.Clock
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

@ExperimentalCoroutinesApi
class IncomeInteractorImplTest {

    private companion object {
        const val notEmptyString = "some string"
        const val emptyString = ""
    }

    private val repository = mockk<IncomeRepository>()
    private val clock = mockk<Clock>()
    private val dateInteractor = mockk<DateInteractor>()
    private val interactor = IncomeInteractorImpl(repository, clock, dateInteractor)

    @Test
    fun `getIncomeForCurrentMonth returns shouldEnterIncome true for empty change date`() =
        runBlockingTest {
            every { dateInteractor.isMonthOfCurrentDateWasEnded(any()) } returns false
            coEvery { repository.getLastChangeDateString() } returns emptyString

            val income = interactor.getIncomeForCurrentMonth()

            assertEquals(BigDecimal.ZERO, income.value)
            assertTrue(income.shouldEnterIncome)
        }


    @Test
    fun `getIncomeForCurrentMonth returns shouldEnterIncome true for first day of month`() =
        runBlockingTest {
            every { dateInteractor.isMonthOfCurrentDateWasEnded(any()) } returns true
            coEvery { repository.getLastChangeDateString() } returns notEmptyString

            val income = interactor.getIncomeForCurrentMonth()

            assertEquals(BigDecimal.ZERO, income.value)
            assertTrue(income.shouldEnterIncome)
        }


    @Test
    fun `getIncomeForCurrentMonth returns shouldEnterIncome false for another cases`() =
        runBlockingTest {
            every { dateInteractor.isMonthOfCurrentDateWasEnded(any()) } returns false
            coEvery { repository.getLastChangeDateString() } returns notEmptyString
            coEvery { repository.getIncomeValue() } returns BigDecimal.TEN

            val income = interactor.getIncomeForCurrentMonth()

            assertEquals(BigDecimal.TEN, income.value)
            assertFalse(income.shouldEnterIncome)
            coVerify(exactly = 1) { repository.getIncomeValue() }
        }
}

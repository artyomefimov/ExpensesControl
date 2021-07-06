package com.artyomefimov.expensescontrol.domain.interactor.income

import com.artyomefimov.expensescontrol.domain.repo.income.IncomeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

@ExperimentalCoroutinesApi
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
    fun `getIncomeForCurrentMonth returns shouldEnterIncome true for empty change date`() =
        runBlockingTest {
            every { clock.now() } returns secondDayOfMonth
            coEvery { repository.getLastChangeDateString() } returns emptyString

            val income = interactor.getIncomeForCurrentMonth()

            assertEquals(BigDecimal.ZERO, income.value)
            assertTrue(income.shouldEnterIncome)
        }


    @Test
    fun `getIncomeForCurrentMonth returns shouldEnterIncome true for first day of month`() =
        runBlockingTest {
            every { clock.now() } returns firstDayOfMonth
            coEvery { repository.getLastChangeDateString() } returns notEmptyString

            val income = interactor.getIncomeForCurrentMonth()

            assertEquals(BigDecimal.ZERO, income.value)
            assertTrue(income.shouldEnterIncome)
        }


    @Test
    fun `getIncomeForCurrentMonth returns shouldEnterIncome false for another cases`() =
        runBlockingTest {
            every { clock.now() } returns secondDayOfMonth
            coEvery { repository.getLastChangeDateString() } returns notEmptyString
            coEvery { repository.getIncomeValue() } returns BigDecimal.TEN

            val income = interactor.getIncomeForCurrentMonth()

            assertEquals(BigDecimal.TEN, income.value)
            assertFalse(income.shouldEnterIncome)
            coVerify(exactly = 1) { repository.getIncomeValue() }
        }
}

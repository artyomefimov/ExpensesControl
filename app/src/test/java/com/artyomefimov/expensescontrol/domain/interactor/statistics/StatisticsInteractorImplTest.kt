package com.artyomefimov.expensescontrol.domain.interactor.statistics

import app.cash.turbine.test
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.domain.model.statistics.PeriodFilter
import com.artyomefimov.expensescontrol.domain.model.statistics.StatisticsFilter
import com.artyomefimov.expensescontrol.domain.repo.expense.ExpenseRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Instant
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal
import java.util.concurrent.Executors
import kotlin.test.assertNull
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class StatisticsInteractorImplTest {

    private companion object {
        val currentDate = Instant.parse("2018-08-20T00:00:00Z")
        val alsoCurrentDate = Instant.parse("2018-08-20T10:00:00Z")
        val dateTomorrowBegin = Instant.parse("2018-08-21T00:00:00Z")
        val dateTomorrow = Instant.parse("2018-08-21T21:00:00Z")
        val expense1 = Expense(
            id = 0,
            sum = BigDecimal.ZERO,
            comment = "",
            category = "Развлечения",
            timestamp = currentDate
        )
        val expense2 = Expense(
            id = 1,
            sum = BigDecimal.ONE,
            comment = "",
            category = "Развлечения",
            timestamp = alsoCurrentDate
        )
        val expense3 = Expense(
            id = 2,
            sum = BigDecimal.TEN,
            comment = "",
            category = "Обязательные",
            timestamp = dateTomorrow
        )
        val expenses = listOf(expense1, expense2, expense3)
    }

    private val expenseRepository = mockk<ExpenseRepository>()
    private val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    private val interactor = StatisticsInteractorImpl(expenseRepository, dispatcher)

    @Test
    fun `applyFilter returns all items if no filters were applied`() = runBlocking {
        every { expenseRepository.allExpenses() } returns flowOf(expenses)
        val filter = StatisticsFilter(
            periodFilter = null,
            categoryFilter = null,
            isMaxSumFilterEnabled = false,
        )
        val expectedSum = expenses.sumOf { it.sum }

        interactor.applyFilter(filter)

        interactor.getFilteringResult().test {
            val result = expectItem()
            assertEquals(expenses, result.expenses)
            assertEquals(expectedSum, result.commonSum)
        }
    }

    @Test
    fun `applyFilter filters expenses by period for single day`() = runBlocking {
        every { expenseRepository.allExpenses() } returns flowOf(expenses)
        val filter = StatisticsFilter(
            periodFilter = PeriodFilter(from = currentDate, to = currentDate),
            categoryFilter = null,
            isMaxSumFilterEnabled = false,
        )
        val expected = listOf(expense1, expense2)
        val expectedSum = expected.sumOf { it.sum }

        interactor.applyFilter(filter)

        interactor.getFilteringResult().test {
            val result = expectItem()
            assertEquals(expected, result.expenses)
            assertEquals(expectedSum, result.commonSum)
        }
    }

    @Test
    fun `applyFilter filters expenses by period for two days`() = runBlocking {
        every { expenseRepository.allExpenses() } returns flowOf(expenses)
        val filter = StatisticsFilter(
            periodFilter = PeriodFilter(from = currentDate, to = dateTomorrowBegin),
            categoryFilter = null,
            isMaxSumFilterEnabled = false,
        )
        val expected = listOf(expense1, expense2, expense3)
        val expectedSum = expected.sumOf { it.sum }

        interactor.applyFilter(filter)

        interactor.getFilteringResult().test {
            val result = expectItem()
            assertEquals(expected, result.expenses)
            assertEquals(expectedSum, result.commonSum)
        }
    }

    @Test
    fun `applyFilter filters expenses by category`() = runBlocking {
        every { expenseRepository.allExpenses() } returns flowOf(expenses)
        val filter = StatisticsFilter(
            periodFilter = null,
            categoryFilter = "Обязательные",
            isMaxSumFilterEnabled = false,
        )
        val expected = listOf(expense3)
        val expectedSum = expense3.sum

        interactor.applyFilter(filter)

        interactor.getFilteringResult().test {
            val result = expectItem()
            assertEquals(expected, result.expenses)
            assertEquals(expectedSum, result.commonSum)
        }
    }

    @Test
    fun `applyFilter filters expenses by max sum`() = runBlocking {
        every { expenseRepository.allExpenses() } returns flowOf(expenses)
        val filter = StatisticsFilter(
            periodFilter = null,
            categoryFilter = null,
            isMaxSumFilterEnabled = true,
        )
        val expected = listOf(expense3)

        interactor.applyFilter(filter)

        interactor.getFilteringResult().test {
            val result = expectItem()
            assertEquals(expected, result.expenses)
            assertNull(result.commonSum)
        }
    }

    @Test
    fun `applyFilter filters expenses by multiple filters`() = runBlocking {
        every { expenseRepository.allExpenses() } returns flowOf(expenses)
        val filter = StatisticsFilter(
            periodFilter = PeriodFilter(from = currentDate, to = dateTomorrowBegin),
            categoryFilter = "Развлечения",
            isMaxSumFilterEnabled = true,
        )
        val expected = listOf(expense2)

        interactor.applyFilter(filter)

        interactor.getFilteringResult().test {
            val result = expectItem()
            assertEquals(expected, result.expenses)
            assertNull(result.commonSum)
        }
    }

    @Test
    fun `graphic is available when only period filter is enable`() = runBlocking {
        every { expenseRepository.allExpenses() } returns flowOf(expenses)
        val filter = StatisticsFilter(
            periodFilter = PeriodFilter(from = currentDate, to = dateTomorrowBegin),
            categoryFilter = null,
            isMaxSumFilterEnabled = false,
        )

        interactor.applyFilter(filter)

        interactor.getFilteringResult().test {
            val result = expectItem()
            assertTrue(result.isGraphicAvailable)
        }
    }

    @Test
    fun `graphic is not available when not only period filter is enable`() = runBlocking {
        every { expenseRepository.allExpenses() } returns flowOf(expenses)
        val filter = StatisticsFilter(
            periodFilter = PeriodFilter(from = currentDate, to = dateTomorrowBegin),
            categoryFilter = "Развлечения",
            isMaxSumFilterEnabled = true,
        )

        interactor.applyFilter(filter)

        interactor.getFilteringResult().test {
            val result = expectItem()
            assertFalse(result.isGraphicAvailable)
        }
    }
}

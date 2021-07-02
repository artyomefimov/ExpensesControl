package com.artyomefimov.expensescontrol.domain.interactor.statistics

import app.cash.turbine.test
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.domain.model.statistics.PeriodFilter
import com.artyomefimov.expensescontrol.domain.model.statistics.StatisticsFilter
import com.artyomefimov.expensescontrol.domain.repo.expense.ExpenseRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.datetime.Instant
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertNull
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class StatisticsInteractorImplTest {

    private companion object {
        val currentDate = Instant.parse("2018-08-20T00:00:00Z")
        val alsoCurrentDate = Instant.parse("2018-08-20T10:00:00Z")
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
    private val interactor = StatisticsInteractorImpl(expenseRepository)

    @Test
    fun `applyFilter returns all items if no filters were applied`() = runBlockingTest {
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
    fun `applyFilter filters expenses by period`() = runBlockingTest {
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
    fun `applyFilter filters expenses by category`() = runBlockingTest {
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
    fun `applyFilter filters expenses by max sum`() = runBlockingTest {
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
    fun `applyFilter filters expenses by multiple filters`() = runBlockingTest {
        every { expenseRepository.allExpenses() } returns flowOf(expenses)
        val filter = StatisticsFilter(
            periodFilter = PeriodFilter(from = dateTomorrow, to = dateTomorrow),
            categoryFilter = "Развлечения",
            isMaxSumFilterEnabled = true,
        )
        val expected = emptyList<Expense>()

        interactor.applyFilter(filter)
        interactor.getFilteringResult().test {
            val result = expectItem()
            assertEquals(expected, result.expenses)
            assertNull(result.commonSum)
        }
    }
}
package com.artyomefimov.expensescontrol.domain.interactor.expense

import android.content.Context
import app.cash.turbine.test
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import com.artyomefimov.expensescontrol.domain.repo.expense.ExpenseRepository
import com.artyomefimov.expensescontrol.domain.repo.income.IncomeRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class DailyExpenseInteractorImplTest {

    private companion object {
        val currentDate = Instant.parse("2018-08-20T10:00:00Z") // days in August = 31
        val dateInNextMonth = Instant.parse("2018-09-20T10:00:00Z")
        const val availableDays = 11

        val expense1 = Expense(
            id = 0, sum = BigDecimal.ZERO, comment = "", category = "", timestamp = currentDate
        )
        val expense2 = Expense(
            id = 1, sum = BigDecimal.ONE, comment = "", category = "", timestamp = dateInNextMonth
        )
        val expense3 = Expense(
            id = 2, sum = BigDecimal.TEN, comment = "", category = "", timestamp = currentDate
        )
        val expenses = listOf(expense1, expense2, expense3)
    }

    private val incomeRepository = mockk<IncomeRepository>()
    private val expenseRepository = mockk<ExpenseRepository>()
    private val clock = mockk<Clock>()
    private val context = mockk<Context>()
    private val interactor = ExpenseInteractorImpl(incomeRepository, expenseRepository, clock, context)

    @Test
    fun `getAvailableDailySum returns zero if monthly income is zero`() = runBlockingTest {
        every { clock.now() } returns currentDate
        coEvery { incomeRepository.getIncomeValue() } returns BigDecimal.ZERO

        val result = interactor.getAvailableDailySum()

        assertEquals(BigDecimal.ZERO, result)
    }

    @Test
    fun `getAvailableDailySum returns average value for days left if monthly income is not zero`() =
        runBlockingTest {
            val expected = 1000
            val incomeValue = (availableDays * expected).toString()
            every { clock.now() } returns currentDate
            coEvery { incomeRepository.getIncomeValue() } returns BigDecimal(incomeValue)

            val result = interactor.getAvailableDailySum()

            assertEquals(BigDecimal(expected.toString()), result)
        }

    @Test
    fun `getExpensesForCurrentMonth returns reversed list of expenses for current month`() =
        runBlockingTest {
            val expected = listOf(expense3, expense2, expense1)
            every { clock.now() } returns currentDate
            every { expenseRepository.getExpensesForCurrentMonth() } returns flowOf(expenses)

            interactor.getExpensesForCurrentMonth().test {
                assertEquals(expected, expectItem())
                expectComplete()
            }

            verify(exactly = 1) { expenseRepository.getExpensesForCurrentMonth() }
        }

    @Test
    fun `getExpensesForCurrentDay returns reversed list of expenses for current month`() =
        runBlockingTest {
            val expected = listOf(expense3, expense2, expense1)
            every { clock.now() } returns currentDate
            every { expenseRepository.getExpensesForCurrentDay() } returns flowOf(expenses)

            interactor.getExpensesForCurrentDay().test {
                assertEquals(expected, expectItem())
                expectComplete()
            }

            verify(exactly = 1) { expenseRepository.getExpensesForCurrentDay() }
        }

    @Test
    fun `getAllExpenses returns reversed list of expenses for current month`() =
        runBlockingTest {
            val expected = listOf(expense3, expense2, expense1)
            every { clock.now() } returns currentDate
            every { expenseRepository.allExpenses() } returns flowOf(expenses)

            interactor.getAllExpenses().test {
                assertEquals(expected, expectItem())
                expectComplete()
            }

            verify(exactly = 1) { expenseRepository.allExpenses() }
        }
}

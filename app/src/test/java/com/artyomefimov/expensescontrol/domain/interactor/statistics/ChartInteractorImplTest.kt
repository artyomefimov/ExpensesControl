package com.artyomefimov.expensescontrol.domain.interactor.statistics

import app.cash.turbine.test
import com.artyomefimov.expensescontrol.domain.model.expense.Expense
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Instant
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal
import java.util.concurrent.Executors
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class ChartInteractorImplTest {

    private companion object {
        val currentDate = Instant.parse("2018-08-20T00:00:00Z")
        const val funCategory = "Развлечения"
        const val mandatoryCategory = "Обязательные"
        const val sportsCategory = "Спорт"
        val expense1 = Expense(
            id = 0,
            sum = BigDecimal("25"),
            comment = "",
            category = funCategory,
            timestamp = currentDate
        )
        val expense2 = Expense(
            id = 1,
            sum = BigDecimal("25"),
            comment = "",
            category = funCategory,
            timestamp = currentDate
        )
        val expense3 = Expense(
            id = 2,
            sum = BigDecimal("40"),
            comment = "",
            category = mandatoryCategory,
            timestamp = currentDate
        )
        val expense4 = Expense(
            id = 2,
            sum = BigDecimal("10"),
            comment = "",
            category = sportsCategory,
            timestamp = currentDate
        )
        val expenses = listOf(expense1, expense2, expense3, expense4)
    }

    private val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    private val interactor = ChartInteractorImpl(dispatcher)

    @Test
    fun `if there are no commands to prepare data then no items in flow`() = runBlocking {
        interactor.getChartData().test {
            expectNoEvents()
        }
    }

    @Test
    fun `data in flow is single shot`() = runBlocking {
        interactor.prepareDataForChart(expenses)

        launch {
            interactor.getChartData().test {
                expectNoEvents()
            }
        }

        Unit
    }

    @Test
    fun `when expenses are empty result is also empty`() = runBlocking {
        val expenses = emptyList<Expense>()

        launch {
            interactor.getChartData().test {
                val result = expectItem()
                assertTrue(result.data.isEmpty())
            }
        }

        interactor.prepareDataForChart(expenses)
    }

    @Test
    fun `when expenses are not empty result is correct`() = runBlocking {
        launch {
            interactor.getChartData().test {
                val result = expectItem().data
                assertTrue(result.isNotEmpty())
                assertEquals(3, result.size)
                assertEquals(BigDecimal("50.00"), result[funCategory])
                assertEquals(BigDecimal("40.00"), result[mandatoryCategory])
                assertEquals(BigDecimal("10.00"), result[sportsCategory])
            }
        }

        interactor.prepareDataForChart(expenses)
    }
}

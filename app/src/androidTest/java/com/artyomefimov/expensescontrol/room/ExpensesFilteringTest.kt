package com.artyomefimov.expensescontrol.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artyomefimov.expensescontrol.data.db.ExpensesDao
import com.artyomefimov.expensescontrol.data.db.ExpensesDb
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExpensesFilteringTest {

    private lateinit var dao: ExpensesDao
    private lateinit var db: ExpensesDb

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            ExpensesDb::class.java
        ).build()
        dao = db.expensesDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addExpenseIsAddingExpenseWithAutogeneratedId() = runBlocking {
        val timestamp = Clock.System.now()
        val expense = TestUtil.createEntityWithTimestamp(timestamp)

        dao.addExpense(entity = expense)
        val result = dao.allExpenses().first().firstOrNull()

        assertTrue(result != null)
        assertNotEquals(expense.id, result!!.id)
        assertEquals(expense.timestamp, result.timestamp)
    }

    @Test
    fun getExpensesForCurrentMonthReturnsActualValues() = runBlocking {
        val lastDayOfPreviousMonth = TestUtil.lastDayOfMonth(ofCurrentMonth = false)
        val firstDay = TestUtil.dayOfCurrentMonth(isFirst = true)
        val middleDay = TestUtil.dayOfCurrentMonth(isFirst = false)
        val lastDayLastMinute = TestUtil.lastDayOfMonth(ofCurrentMonth = true)
        val firstDayOfNextMonth = TestUtil.dayOfNextMonth()
        val entities = TestUtil.createEntitiesWithTimestamps(
            lastDayOfPreviousMonth,
            firstDay,
            middleDay,
            lastDayLastMinute,
            firstDayOfNextMonth,
        )

        entities.forEach {
            dao.addExpense(it)
        }
        val currentMonthExpenses = dao.getExpensesForCurrentMonth().first()

        assertNull(
            "should not have lastDayOfPreviousMonth",
            currentMonthExpenses.find { it.timestamp == lastDayOfPreviousMonth }
        )
        assertNotNull(
            "should have firstDay",
            currentMonthExpenses.find { it.timestamp == firstDay }
        )
        assertNotNull(
            "should have middleDay",
            currentMonthExpenses.find { it.timestamp == middleDay }
        )
        assertNotNull(
            "should have lastDayLastMinute",
            currentMonthExpenses.find { it.timestamp == lastDayLastMinute }
        )
        assertNull(
            "should not have firstDayOfNextMonth",
            currentMonthExpenses.find { it.timestamp == firstDayOfNextMonth }
        )
    }

    @Test
    fun getExpensesForCurrentDayReturnsActualValues() = runBlocking {
        val startOfTheDay = TestUtil.startOfTheDay()
        val middleOfTheDay = TestUtil.middleOfTheDay()
        val endOfTheDay = TestUtil.endOfTheDay()
        val previousDay = TestUtil.previousDay()
        val nextDay = TestUtil.nextDay()
        val entities = TestUtil.createEntitiesWithTimestamps(
            startOfTheDay,
            middleOfTheDay,
            endOfTheDay,
            previousDay,
            nextDay
        )

        entities.forEach {
            dao.addExpense(it)
        }
        val currentDayExpenses = dao.getExpensesForCurrentDay().first()

        assertNotNull(
            "should have startOfTheDay",
            currentDayExpenses.find { it.timestamp == startOfTheDay }
        )
        assertNotNull(
            "should have middleOfTheDay",
            currentDayExpenses.find { it.timestamp == middleOfTheDay }
        )
        assertNotNull(
            "should have endOfTheDay",
            currentDayExpenses.find { it.timestamp == endOfTheDay }
        )
        assertNull(
            "should not have previousDay",
            currentDayExpenses.find { it.timestamp == previousDay }
        )
        assertNull(
            "should not have nextDay",
            currentDayExpenses.find { it.timestamp == nextDay }
        )
    }
}

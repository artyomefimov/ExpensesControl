package com.artyomefimov.expensescontrol.domain.interactor.date

import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DateInteractorImplTest {

    private companion object {
        // days in August = 31
        val currentDate = Instant.parse("2018-08-20T00:00:00Z")
        val endOfCurrentDate = Instant.parse("2018-08-20T23:59:59Z")
        val nextDate = Instant.parse("2018-08-21T00:00:00Z")
        val firstDayOfMonth = Instant.parse("2018-08-01T00:00:00Z")
    }

    private val clock = mockk<Clock>()
    private val interactor = DateInteractorImpl(clock)

    @Test
    fun `getCurrentMonthNameAndLastDay returns last day number and formatted month name`() {
        every { clock.now() } returns currentDate

        val result = interactor.getCurrentMonthNameAndLastDay()

        assertEquals("31 августа", result)
    }

    @Test
    fun `availableDaysInThisMonth returns number of days until the end of the month`() {
        every { clock.now() } returns currentDate

        val result = interactor.availableDaysInThisMonth()

        assertEquals(11, result)
    }

    @Test
    fun `isFirstDayOfMonth returns false for not first day`() {
        every { clock.now() } returns currentDate

        assertFalse(interactor.isFirstDayOfMonth())
    }

    @Test
    fun `isFirstDayOfMonth returns true for first day`() {
        every { clock.now() } returns firstDayOfMonth

        assertTrue(interactor.isFirstDayOfMonth())
    }

    @Test
    fun `daysRange returns range for begin and end of current day if from and to are equal`() {
        val expected = currentDate..nextDate

        val result = interactor.daysRange(currentDate, currentDate)

        assertEquals(expected, result)
    }

    @Test
    fun `daysRange returns range between two days if from and to are not equal`() {
        val expected = firstDayOfMonth..endOfCurrentDate

        val result = interactor.daysRange(firstDayOfMonth, currentDate)

        assertEquals(expected, result)
    }
}

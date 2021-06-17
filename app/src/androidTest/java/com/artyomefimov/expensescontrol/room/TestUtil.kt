package com.artyomefimov.expensescontrol.room

import com.artyomefimov.expensescontrol.data.model.ExpenseEntity
import kotlinx.datetime.*
import java.math.BigDecimal

object TestUtil {

    fun createEntityWithTimestamp(timestamp: Instant) = ExpenseEntity(
        id = 0,
        sum = BigDecimal.ZERO,
        comment = "",
        category = "",
        timestamp = timestamp
    )

    fun createEntitiesWithTimestamps(vararg timestamps: Instant): List<ExpenseEntity> {
        val result = mutableListOf<ExpenseEntity>()
        timestamps.forEach { timestamp ->
            result.add(
                ExpenseEntity(
                    id = 0,
                    sum = BigDecimal.ZERO,
                    comment = "",
                    category = "",
                    timestamp = timestamp
                )
            )
        }
        return result
    }

    fun dayOfCurrentMonth(isFirst: Boolean): Instant {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val year = now.year
        val monthNumber = if (now.monthNumber in 1..9) "0${now.monthNumber}" else now.monthNumber
        val day = if (isFirst) 1 else now.dayOfMonth
        val dayNumber = numberForParsing(day)

//        "2018-08-01T00:00:00Z"
        return Instant.parse("$year-$monthNumber-${dayNumber}T00:00:00Z")
    }

    fun lastDayOfMonth(ofCurrentMonth: Boolean): Instant {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val isLeap = now.toJavaLocalDateTime().toLocalDate().isLeapYear

        val previousMonthNumber = if (ofCurrentMonth) now.monthNumber else now.monthNumber - 1
        val year = if (ofCurrentMonth.not() && previousMonthNumber == 12) now.year - 1 else now.year
        val monthNumber = numberForParsing(previousMonthNumber)
        val lastDay = Month.of(previousMonthNumber).length(isLeap) - 1

//        "2018-07-31T23:59:59Z"
        return Instant.parse("$year-$monthNumber-${lastDay}T23:59:59Z")
    }

    fun dayOfNextMonth(): Instant {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val year = now.year
        val next = now.monthNumber + 1
        val month = if (next <= 12) next else 1
        val monthNumber = numberForParsing(month)
        val day = numberForParsing(now.dayOfMonth)

//        "2018-09-01T00:00:00Z"
        return Instant.parse("$year-$monthNumber-${day}T00:00:00Z")
    }

    fun startOfTheDay(): Instant {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
        val date = now.substring(0, now.indexOf('T'))

        return Instant.parse("${date}T00:00:00Z")
    }

    fun middleOfTheDay(): Instant {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
        val date = now.substring(0, now.indexOf('T'))

        return Instant.parse("${date}T12:35:31Z")
    }

    fun endOfTheDay(): Instant {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
        val date = now.substring(0, now.indexOf('T'))

        return Instant.parse("${date}T23:59:59Z")
    }

    fun previousDay(): Instant {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val year = now.year
        val day = now.dayOfMonth - 1
        val dayNumber = numberForParsing(day)
        val month = if (day >= 30) now.monthNumber - 1 else now.monthNumber
        val monthNumber = numberForParsing(month)

        return Instant.parse("$year-$monthNumber-${dayNumber}T23:59:59Z")
    }

    fun nextDay(): Instant {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val year = now.year
        val day = now.dayOfMonth + 1
        val dayNumber = numberForParsing(day)
        val month = if (day == 1) now.monthNumber + 1 else now.monthNumber
        val monthNumber = numberForParsing(month)

        return Instant.parse("$year-$monthNumber-${dayNumber}T00:00:00Z")
    }

    private fun numberForParsing(number: Int): String {
        return if (number in 1..9) "0$number" else number.toString()
    }
}

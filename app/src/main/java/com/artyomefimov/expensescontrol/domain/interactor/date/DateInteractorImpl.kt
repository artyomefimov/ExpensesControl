package com.artyomefimov.expensescontrol.domain.interactor.date

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.time.Duration
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class DateInteractorImpl @Inject constructor(
    private val clock: Clock,
): DateInteractor {

    private companion object {
        const val FIRST_DAY = 1
    }

    private val formatter = DateTimeFormatter
        .ofPattern("MMMM")
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    override fun daysRange(
        instantFrom: Instant,
        instantTo: Instant
    ): ClosedRange<Instant> {
        return if (instantFrom == instantTo) {
            val beginOfNextDay = instantTo
                .toJavaInstant()
                .plus(Duration.ofDays(1))
                .toKotlinInstant()
            instantFrom..beginOfNextDay
        } else {
            val endOfNextDay = instantTo.toJavaInstant()
                .plus(Duration.ofDays(1))
                .minus(Duration.ofSeconds(1))
                .toKotlinInstant()
            instantFrom..endOfNextDay
        }
    }

    override fun availableDaysInThisMonth(): Int {
        val today = today()
        return getCurrentMonthLength(today) - today.dayOfMonth
    }

    override fun isFirstDayOfMonth(): Boolean {
        return today().dayOfMonth == FIRST_DAY
    }

    override fun getCurrentMonthNameAndLastDay(): String {
        val today = today()
        val lastDayOfCurrentMonth = getCurrentMonthLength(today)
        return buildString {
            append(lastDayOfCurrentMonth)
            append(" ")
            append(formatter.format(clock.now().toJavaInstant()))
        }
    }

    private fun today(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): LocalDateTime {
        return clock.now().toLocalDateTime(timeZone)
    }

    private fun getCurrentMonthLength(today: LocalDateTime): Int {
        val isLeap = today.toJavaLocalDateTime().toLocalDate().isLeapYear
        return today.month.length(isLeap)
    }
}

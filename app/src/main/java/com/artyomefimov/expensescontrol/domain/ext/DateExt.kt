package com.artyomefimov.expensescontrol.domain.ext

import kotlinx.datetime.*
import java.time.Duration

/**
 * Возвращает [LocalDateTime] сегодняшнего дня
 */
fun today(
    clock: Clock,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): LocalDateTime = clock.now().toLocalDateTime(timeZone)

/**
 * Возвращает [ClosedRange] между двумя датами
 */
fun daysRange(
    instantFrom: Instant,
    instantTo: Instant,
): ClosedRange<Instant> {
    return if (instantFrom == instantTo) {
        val beginOfNextDay = instantTo.toJavaInstant().plus(Duration.ofDays(1))
        instantFrom..beginOfNextDay.toKotlinInstant()
    } else {
        instantFrom..instantTo
    }
}

/**
 * Краткая форма получения [Instant] из [Long]
 */
fun Long.toInstant() = Instant.fromEpochMilliseconds(this)
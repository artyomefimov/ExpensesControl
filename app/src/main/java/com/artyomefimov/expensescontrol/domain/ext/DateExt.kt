package com.artyomefimov.expensescontrol.domain.ext

import kotlinx.datetime.*
import java.time.Duration

fun today(
    clock: Clock,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): LocalDateTime = clock.now().toLocalDateTime(timeZone)

fun hoursDiffAtMillis(
    diffInHours: Long,
): Long {
    val now = Clock.System.now()
    val twelveHoursOffset = now.toJavaInstant().plus(Duration.ofHours(diffInHours))
    return twelveHoursOffset.toEpochMilli() - now.toEpochMilliseconds()
}

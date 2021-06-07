package com.artyomefimov.expensescontrol.domain.ext

import kotlinx.datetime.*

fun today(
    clock: Clock,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): LocalDateTime = clock.now().toLocalDateTime(timeZone)

fun Instant.isInCurrentMonth(
    clock: Clock,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): Boolean = toLocalDateTime(timeZone).month == today(clock).month
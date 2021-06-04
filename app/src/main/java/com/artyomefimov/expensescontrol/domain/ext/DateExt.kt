package com.artyomefimov.expensescontrol.domain.ext

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun today(
    clock: Clock,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime = clock.now().toLocalDateTime(timeZone)
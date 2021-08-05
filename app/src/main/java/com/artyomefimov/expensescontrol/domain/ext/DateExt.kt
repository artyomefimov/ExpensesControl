package com.artyomefimov.expensescontrol.domain.ext

import kotlinx.datetime.Instant

/**
 * Краткая форма получения [Instant] из [Long]
 */
fun Long.toInstant() = Instant.fromEpochMilliseconds(this)

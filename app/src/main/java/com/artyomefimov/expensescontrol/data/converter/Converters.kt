package com.artyomefimov.expensescontrol.data.converter

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import java.math.BigDecimal

class Converters {

    @TypeConverter
    fun fromBigDecimal(value: BigDecimal) = value.toString()

    @TypeConverter
    fun toBigDecimal(from: String) = BigDecimal(from)

    @TypeConverter
    fun fromInstant(value: Instant) = value.toString()

    @TypeConverter
    fun toInstant(from: String) = Instant.parse(from)
}
package com.artyomefimov.expensescontrol.presentation.ext

import java.text.DecimalFormat
import java.text.NumberFormat

const val COMMA = ','
const val POINT = '.'
val decimalNumbersRegex = "[^0-9.]+".toRegex()
val integerFormatter: NumberFormat = DecimalFormat.getCurrencyInstance().apply {
    maximumFractionDigits = 0
}
val fractionFormatter: NumberFormat = DecimalFormat.getCurrencyInstance()

fun String?.formatToAmount(): String? = this
    ?.replace(COMMA, POINT)
    ?.replace(decimalNumbersRegex, "")
    ?.run {
        if (count { it == POINT } > 1) {
            val indexOfLastPointBeforeFractionPart = lastIndexOf(POINT)
            val integerPart = substring(0, indexOfLastPointBeforeFractionPart)
            return@run integerPart.replace(POINT.toString(), "")
        } else {
            return@run this
        }
    }

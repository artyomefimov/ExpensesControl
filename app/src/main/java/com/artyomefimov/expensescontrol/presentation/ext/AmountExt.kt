package com.artyomefimov.expensescontrol.presentation.ext

import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * Форматирует числа, отбрасывая дробную часть
 */
val integerFormatter: NumberFormat = DecimalFormat.getCurrencyInstance().apply {
    maximumFractionDigits = 0
}
/**
 * Форматирует числа, с сохранением двух цифр дробной части
 */
val fractionFormatter: NumberFormat = DecimalFormat.getCurrencyInstance()

const val COMMA = ','
const val POINT = '.'
val decimalNumbersRegex = "[^0-9.]+".toRegex()

/**
 * Преобразует строку для формата BigDecimal
 */
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

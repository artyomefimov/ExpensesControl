package com.artyomefimov.expensescontrol.presentation.view.compose.common.components.edittext

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.artyomefimov.expensescontrol.presentation.ext.COMMA
import com.artyomefimov.expensescontrol.presentation.ext.POINT
import com.artyomefimov.expensescontrol.presentation.ext.formatToAmount
import com.artyomefimov.expensescontrol.presentation.ext.integerFormatter
import java.math.BigDecimal

/**
 * Преобразует введенную сумму в более читаемый вид:
 * с пробелами между тысячами, графемой валюты текущей локали
 *
 * @param input значение поля ввода суммы
 *
 * @return [TextFieldValue] значение с отформатированным текстом
 */
fun formatMoneyString(input: TextFieldValue): TextFieldValue {

    val text = input.text.formatToAmount()

    if (text.isNullOrEmpty()) {
        return input
    }

    return try {
        val amount = BigDecimal(text)
        val formattedAmountText = integerFormatter.format(amount)
        TextFieldValue(
            text = formattedAmountText,
            selection = TextRange(getCursorPosition(formattedAmountText))
        )
    } catch (e: NumberFormatException) {
        TextFieldValue(text = "")
    }
}

private fun getCursorPosition(text: String): Int {
    val indexOfComma = text.indexOf(COMMA)
    val hasComma = indexOfComma >=0
    val indexOfPoint = text.indexOf(POINT)
    val hasPoint = indexOfPoint >= 0
    val indexOfLastNumberBeforeCurrency = text.length - 2
    return when {
        hasComma && hasPoint -> indexOfPoint
        hasComma -> indexOfComma
        hasPoint -> indexOfPoint
        indexOfLastNumberBeforeCurrency >= 0 -> indexOfLastNumberBeforeCurrency
        else -> text.length
    }
}

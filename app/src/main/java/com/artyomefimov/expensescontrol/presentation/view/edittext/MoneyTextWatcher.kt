package com.artyomefimov.expensescontrol.presentation.view.edittext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.artyomefimov.expensescontrol.presentation.ext.COMMA
import com.artyomefimov.expensescontrol.presentation.ext.formatToAmount
import java.math.BigDecimal
import java.text.NumberFormat

class MoneyTextWatcher(
    private val editText: EditText,
    private val formatter: NumberFormat,
) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    override fun afterTextChanged(s: Editable?) {
        val text = s?.toString()?.formatToAmount()

        if (text.isNullOrEmpty()) {
            return
        }

        editText.removeTextChangedListener(this)
        val amount = BigDecimal(text)
        val formatted = formatter.format(amount)
        editText.setText(formatted)
        editText.setSelection(getCursorPosition(formatted))
        editText.addTextChangedListener(this)
    }

    private fun getCursorPosition(text: String): Int {
        val indexOfComma = text.indexOf(COMMA)
        val indexOfLastNumberBeforeCurrency = text.length - 2
        return when {
            indexOfComma >= 0 -> indexOfComma
            indexOfLastNumberBeforeCurrency >= 0 -> indexOfLastNumberBeforeCurrency
            else -> text.length
        }
    }
}

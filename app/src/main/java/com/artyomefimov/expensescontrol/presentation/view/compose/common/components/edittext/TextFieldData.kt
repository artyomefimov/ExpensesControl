package com.artyomefimov.expensescontrol.presentation.view.compose.common.components.edittext

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

/**
 * Значение поля ввода с пустым текстом и selection в начале
 */
fun emptyTextFieldValue() = TextFieldValue("", TextRange.Zero)

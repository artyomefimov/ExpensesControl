package com.artyomefimov.expensescontrol.presentation.view.compose.common.components.edittext

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

/**
 * Поле ввода суммы с возможностью очистки введенного текста
 *
 * @param placeholderTextResId id ресурса для текста плейсхолдера
 * @param value                значение поля ввода для отображения
 * @param onTextChanged        действие по изменению введенного текста
 */
@Composable
fun MoneyTextField(
    @StringRes placeholderTextResId: Int,
    value: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue ->
            val result = formatMoneyString(newValue)
            onTextChanged(result)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        trailingIcon = {
            if (value.text.isNotBlank()) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onTextChanged(emptyTextFieldValue())
                    }
                )
            }
        },
        placeholder = {
            Text(
                text = stringResource(id = placeholderTextResId),
                color = MaterialTheme.colors.onSurface,
            )
        }
    )
}

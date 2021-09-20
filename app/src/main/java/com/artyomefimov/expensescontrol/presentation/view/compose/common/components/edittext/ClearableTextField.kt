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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

/**
 * Поле ввода текста с возможностью очистки введенного текста
 *
 * @param placeholderTextResId id ресурса для текста плейсхолдера
 * @param text                 текст поля ввода для отображения
 * @param onTextChanged        действие по изменению введенного текста
 */
@Composable
fun ClearableTextField(
    @StringRes placeholderTextResId: Int,
    text: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = onTextChanged,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences,
        ),
        trailingIcon = {
            if (text.isNotBlank()) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onTextChanged("")
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

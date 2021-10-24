package com.artyomefimov.expensescontrol.presentation.view.compose.common.components.chip

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artyomefimov.expensescontrol.presentation.model.ChipData
import com.artyomefimov.expensescontrol.presentation.model.expense.ExpensesChipData

/**
 * Material chip. Имеет состояния selected, not selected.
 * В selected состоянии показывается текст чипа и иконка галочки
 * В not selected состоянии показывается текст чипа
 *
 * @param data       информация для отображения
 * @param isSelected true, если чип должен быть в состоянии selected, false - иначе
 * @param onClick    действие по нажатию на чип
 */
@Composable
fun <T : ChipData> Chip(
    modifier: Modifier = Modifier,
    data: T,
    isSelected: Boolean,
    onClick: (T) -> Unit,
) {
    Surface(
        modifier = modifier.animateContentSize(),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        color = when {
            isSelected -> MaterialTheme.colors.secondary
            else -> MaterialTheme.colors.secondary.copy(alpha = 0.6f)
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .toggleable(
                    value = isSelected,
                    onValueChange = {
                        onClick(data)
                    }
                )
                .padding(
                    horizontal = 8.dp,
                    vertical = 6.dp,
                ),
        ) {
            Text(
                text = data.title,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSecondary,
            )
            Spacer(
                modifier = Modifier.width(
                    if (isSelected) 8.dp else 32.dp
                )
            )
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    name = "Selected light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    showBackground = true,
    name = "Selected dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun ChipsPreview() {
    Column {
        Chip(data = ExpensesChipData(title = "same text"), onClick = {}, isSelected = true)
        Chip(data = ExpensesChipData(title = "same text"), onClick = {}, isSelected = false)
    }
}
